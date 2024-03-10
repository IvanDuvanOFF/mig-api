package org.example.migapi.domain.dto.auth

data class SignInRequest(
    val username: String,
    val password: String
)
