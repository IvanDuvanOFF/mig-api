package org.example.migapi.domain.dto.auth

data class SignResponse(
    val token: String,
    val refreshToken: String
)
