package org.example.migapi.controller

import org.example.migapi.domain.dto.AdminDto
import org.springframework.http.HttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/signup")
class SignUpController {
    @PostMapping("admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addAdmin(adminDto: AdminDto, httpRequest: HttpRequest)
}