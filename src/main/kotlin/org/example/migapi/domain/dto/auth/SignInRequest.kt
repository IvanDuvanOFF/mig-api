package org.example.migapi.domain.dto.auth

data class SignInRequest(
    val usernameOrEmail: String,
    val password: String
)
