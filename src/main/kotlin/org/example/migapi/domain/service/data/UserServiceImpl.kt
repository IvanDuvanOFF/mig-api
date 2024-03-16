package org.example.migapi.domain.service.data

import jakarta.persistence.PersistenceException
import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignRequest
import org.example.migapi.domain.dto.auth.SignResponse
import org.example.migapi.domain.model.SpringUser
import org.example.migapi.domain.model.User
import org.example.migapi.domain.model.enums.ERole
import org.example.migapi.domain.service.security.JwtService
import org.example.migapi.exception.UserAlreadyExistsException
import org.example.migapi.repository.RoleRepository
import org.example.migapi.repository.UserRepository
import org.example.migapi.repository.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.jvm.Throws

@Service
class UserServiceImpl(
    @Autowired
    private val dtoService: DtoService,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository,
    @Autowired
    private val verificationTokenRepository: VerificationTokenRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val authenticationManager: AuthenticationManager,
    @Autowired
    private val jwtService: JwtService,
    @Value("\${mig.jwt.verification-expiration}")
    private val verificationTokenExpiration: Int
) : UserService {
    override fun saveUser(signRequest: SignRequest, request: HttpServletRequest): User {
        if (userExists(signRequest.username))
            throw NullPointerException("Username or email is already taken")


        val role = roleRepository.findById(ERole.ROLE_USER.name)
            .orElseThrow { NullPointerException("No role ${ERole.ROLE_USER.name} does not exist") }

        val user = User(
            username = signRequest.username,
            password = passwordEncoder.encode(signRequest.password),
            role = role,
            isActive = true,

            name = "",
            surname = "",
            birthday = LocalDate.now()
        )

        return userRepository.save(user)
    }

    override fun createUser(userDto: UserDto): User {
        if (userExists(userDto.username))
            throw UserAlreadyExistsException("Username is already exists")

        val user = dtoService.toUser(userDto).apply { password = passwordEncoder.encode(password) }

        return userRepository.save(user)
    }

    override fun signIn(signRequest: SignRequest): SignResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signRequest.username,
                signRequest.password
            )
        )

        val userDetails = authentication.principal as SpringUser

        val jwt = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), userDetails)

        return SignResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignResponse {
        val username = jwtService.extractUsername(refreshTokenRequest.refreshToken)
        val userDetails = userRepository.findUserByUsername(username)
            .orElseThrow { NullPointerException("No user found") }.toSpringUser()

        if (!jwtService.isTokenValid(refreshTokenRequest.refreshToken, userDetails))
            throw NullPointerException("Token expired")

        val jwt = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), userDetails)

        return SignResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    private fun findUserByVerificationToken(token: String): User {
        val verificationToken = verificationTokenRepository.findById(UUID.fromString(token))

        if (verificationToken.isEmpty)
            throw NullPointerException("Verification token has not been found")
        if (verificationToken.get().expirationDate.isBefore(LocalDateTime.now()))
            throw NullPointerException("Verification token has been expired")

        val user = verificationToken.get().user

        if (user.isActive)
            throw NullPointerException("Your account has been already activated")

        return user
    }

    @Throws(exceptionClasses = [PersistenceException::class])
    fun userExists(username: String): Boolean =
        userRepository.findUserByUsername(username).isPresent
}