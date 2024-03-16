package org.example.migapi.domain.service.data

import io.jsonwebtoken.JwtException
import jakarta.persistence.PersistenceException
import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignRequest
import org.example.migapi.domain.dto.auth.SignResponse
import org.example.migapi.domain.model.User
import org.example.migapi.exception.RoleNotFoundException
import org.example.migapi.exception.TokenExpiredException
import org.example.migapi.exception.UserAlreadyExistsException
import org.example.migapi.exception.UserNotFoundException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException

interface UserService {
    @Throws(exceptionClasses = [UserAlreadyExistsException::class, RoleNotFoundException::class, PersistenceException::class])
    fun saveUser(signRequest: SignRequest, request: HttpServletRequest): User

    fun createUser(userDto: UserDto): User

    @Throws(exceptionClasses = [UserNotFoundException::class, DisabledException::class, LockedException::class, PersistenceException::class])
    fun signIn(signRequest: SignRequest): SignResponse

    @Throws(exceptionClasses = [JwtException::class, TokenExpiredException::class, UserNotFoundException::class, PersistenceException::class])
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignResponse
}