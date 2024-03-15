package org.example.migapi.controller

import org.example.migapi.domain.dto.StudentDto
import org.example.migapi.domain.model.enums.ERole
import org.springframework.http.HttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/")
class AdminController {

    @PostMapping("add/student")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addStudent(studentDto: StudentDto, httpRequest: HttpRequest) {

    }
}
