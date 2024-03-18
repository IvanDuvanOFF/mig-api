package org.example.migapi.domain.service.security

interface TotpService {
    fun generateSecret(): String

    fun generateCode(secret: String): String

    fun validateCode(otp: String, )
}