package org.example.migapi.controller.auth

import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignInRequest
import org.example.migapi.domain.dto.auth.SignInResponse
import org.example.migapi.domain.dto.util.Redirect
import org.example.migapi.domain.service.UserService
import org.example.migapi.utils.MigUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*

@RestController("/api/auth")
class AuthenticationController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val mailSender: JavaMailSender,
    @Autowired
    private val migUtils: MigUtils
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: UserDto, request: HttpServletRequest): Redirect {
        userService.saveUser(signUpRequest, request)

        val url = migUtils.getHostUrl(request)

        return Redirect("$url/api/auth/signin")
    }

    @GetMapping("/signup/confirm")
    fun confirmation(@RequestParam token: String, request: HttpServletRequest): Redirect {
        userService.activateUser(token)

        val url = migUtils.getHostUrl(request)

        return Redirect(url = url)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): SignInResponse = userService.signIn(signInRequest)

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignInResponse =
        userService.refreshToken(refreshTokenRequest)
}