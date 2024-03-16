package org.example.migapi.controller.auth

import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignRequest
import org.example.migapi.domain.dto.auth.SignResponse
import org.example.migapi.domain.service.data.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    @Autowired
    private val userService: UserService
) {

    @PostMapping("signin")
    fun signIn(@RequestBody signRequest: SignRequest): SignResponse = userService.signIn(signRequest)

    @PostMapping("refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignResponse =
        userService.refreshToken(refreshTokenRequest)
}