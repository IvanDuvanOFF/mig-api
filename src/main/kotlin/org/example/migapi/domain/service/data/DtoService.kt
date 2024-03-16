package org.example.migapi.domain.service.data

import org.example.migapi.domain.dto.AdminDto
import org.example.migapi.domain.dto.StudentDto
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.model.User

interface DtoService {
    fun toUser(userDto: UserDto): User

    fun userToUserDto(user: User): UserDto

    fun userToAdminDto(user: User): AdminDto

    fun userToStudentDto(user: User): StudentDto

    fun studentDtoToUser(studentDto: StudentDto): User

    fun adminDtoToUser(adminDto: AdminDto): User

    fun userDtoToUser(userDto: UserDto): User
}