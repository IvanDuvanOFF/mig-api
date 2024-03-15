package org.example.migapi.domain.dto

import org.example.migapi.domain.model.enums.ERole
import org.example.migapi.domain.model.Model
import org.example.migapi.domain.model.Role
import org.example.migapi.domain.model.User
import java.util.*

open class UserDto(
    val id: String? = null,
    val username: String,
    val password: String,
    val isActive: Boolean = false,
    val role: String = ERole.ROLE_USER.name
) : Dto
