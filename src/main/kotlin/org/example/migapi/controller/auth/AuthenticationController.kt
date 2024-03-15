package org.example.migapi.controller.auth

import jakarta.servlet.http.HttpServletRequest
import org.example.migapi.domain.dto.auth.RefreshTokenRequest
import org.example.migapi.domain.dto.auth.SignRequest
import org.example.migapi.domain.dto.auth.SignResponse
import org.example.migapi.domain.dto.util.Redirect
import org.example.migapi.domain.service.data.UserService
import org.example.migapi.utils.MigUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val mailSender: JavaMailSender,
    @Autowired
    private val migUtils: MigUtils
) {

    @PostMapping("signup")
    fun signUp(@RequestBody signUpRequest: SignRequest, request: HttpServletRequest): Redirect {
        userService.saveUser(signUpRequest, request)

        val url = migUtils.getHostUrl(request)

        return Redirect("$url/api/auth/signin")
    }

    @PostMapping("signin")
    fun signIn(@RequestBody signRequest: SignRequest): SignResponse = userService.signIn(signRequest)

    @PostMapping("refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignResponse =
        userService.refreshToken(refreshTokenRequest)
}