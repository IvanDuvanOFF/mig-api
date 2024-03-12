package org.example.migapi.domain.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignInRequest
import org.example.migapi.domain.dto.auth.SignInResponse
import org.example.migapi.domain.model.SpringUser
import org.example.migapi.domain.model.User
import org.example.migapi.domain.model.VerificationToken
import org.example.migapi.domain.service.JwtService
import org.example.migapi.domain.service.UserService
import org.example.migapi.repository.RoleRepository
import org.example.migapi.repository.UserRepository
import org.example.migapi.repository.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class UserServiceImpl(
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
    override fun saveUser(userDto: UserDto, request: HttpServletRequest): User {
        if (userExists(userDto))
            throw NullPointerException("Username or email is already taken")


        val role = roleRepository.findById("ROLE_USER")
            .orElseThrow { NullPointerException("No role ROLE_USER exists") }

        val user = User(
            username = userDto.username,
            password = passwordEncoder.encode(userDto.password),
            role = role,
            isActive = true
        )

        return userRepository.save(user)
    }

    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.username,
                signInRequest.password
            )
        )

        val userDetails = authentication.principal as SpringUser

        val jwt = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), userDetails)

        return SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse {
        val username = jwtService.extractUsername(refreshTokenRequest.refreshToken)
        val userDetails = userRepository.findUserByUsername(username)
            .orElseThrow { NullPointerException("No user found") }.toSpringUser()

        if (!jwtService.isTokenValid(refreshTokenRequest.refreshToken, userDetails))
            throw NullPointerException("Token expired")

        val jwt = jwtService.generateToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), userDetails)

        return SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun prepareEmail(user: User, url: String): SimpleMailMessage {
        TODO("Not need yet")
    }

    override fun activateUser(token: String): User {
        TODO("Not yet implemented")
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

    fun userExists(userDto: UserDto): Boolean =
        userRepository.findUserByUsername(userDto.username).isPresent
}