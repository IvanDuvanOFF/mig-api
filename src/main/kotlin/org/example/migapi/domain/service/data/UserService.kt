package org.example.migapi.domain.service.data

import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignRequest
import org.example.migapi.domain.dto.auth.SignResponse
import org.example.migapi.domain.model.User

interface UserService {
    fun saveUser(signRequest: SignRequest, request: HttpServletRequest): User

    fun signIn(signRequest: SignRequest): SignResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignResponse
}