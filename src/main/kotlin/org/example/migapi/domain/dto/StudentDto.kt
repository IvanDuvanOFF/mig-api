package org.example.migapi.domain.dto

import java.util.Date

class StudentDto(
    id: String? = null,
    username: String,
    password: String,
    isActive: Boolean,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val email: String,
    val phone: String,
    val country: String,
    val birthday: Date,
    val status: String
) : UserDto(id, username, password, isActive, "ROLE_USER")