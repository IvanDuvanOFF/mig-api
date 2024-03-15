package org.example.migapi.domain.service.security

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {
    fun generateToken(userDetails: UserDetails): String

    fun generateRefreshToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String

    fun extractUsername(token: String): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}