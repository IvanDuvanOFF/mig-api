package org.example.migapi.domain.dto.auth

data class SignInResponse(
    val token: String,
    val refreshToken: String
)
