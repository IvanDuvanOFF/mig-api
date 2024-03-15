package org.example.migapi.domain.service

import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignInRequest
import org.example.migapi.domain.dto.auth.SignInResponse
import org.example.migapi.domain.model.User
import org.springframework.mail.SimpleMailMessage

interface UserService {
    fun saveUser(userDto: UserDto, request: HttpServletRequest): User

    fun signIn(signInRequest: SignInRequest): SignInResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse
}