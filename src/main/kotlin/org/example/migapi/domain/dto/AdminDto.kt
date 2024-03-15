package org.example.migapi.domain.dto

class AdminDto(
    id: String? = null,
    username: String,
    password: String,
    isActive: Boolean,
    val name: String,
    val surname: String
) : UserDto(id, username, password, isActive, "ROLE_ADMIN")