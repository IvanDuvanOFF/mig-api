package org.example.migapi.domain.service.data

import org.example.migapi.domain.dto.AdminDto
import org.example.migapi.domain.dto.StudentDto
import org.example.migapi.domain.dto.UserDto
import org.example.migapi.domain.model.Country
import org.example.migapi.domain.model.Role
import org.example.migapi.domain.model.StudentStatus
import org.example.migapi.domain.model.User
import org.example.migapi.domain.model.enums.ERole
import org.example.migapi.domain.model.enums.EStudentStatus
import org.example.migapi.domain.service.data.DtoService
import java.time.LocalDate
import java.util.*

class DtoServiceImpl : DtoService {
    fun userToUserDto(user: User): UserDto = UserDto(
        id = user.id.toString(),
        username = user.username,
        password = user.password,
        isActive = user.isActive,
        role = user.role.name.name
    )

    fun userToAdminDto(user: User): AdminDto = AdminDto(
        id = user.id.toString(),
        username = user.username,
        password = user.password,
        isActive = user.isActive,
        name = user.name,
        surname = user.surname
    )

    fun userToStudentDto(user: User): StudentDto = StudentDto(
        id = user.id.toString(),
        username = user.username,
        password = user.password,
        isActive = user.isActive,
        name = user.name,
        surname = user.surname,
        patronymic = user.patronymic,
        email = user.email,
        phone = user.phone,
        country = user.country.name,
        birthday = Date(user.birthday.toEpochDay()),
        status = user.status.name.name
    )

    fun studentDtoToUser(studentDto: StudentDto): User = User(
        id = if (studentDto.id == null)
            UUID.randomUUID()
        else
            UUID.fromString(studentDto.id),
        username = studentDto.username,
        password = studentDto.password,
        role = Role(ERole.valueOf(studentDto.role)),
        isActive = studentDto.isActive,
        name = studentDto.name,
        surname = studentDto.surname,
        patronymic = studentDto.patronymic,
        email = studentDto.email,
        phone = studentDto.phone,
        // todo add check for country existing
        country = Country(studentDto.country),
        // may cause problems
        birthday = LocalDate.parse(studentDto.birthday.toString()),
        status = StudentStatus(EStudentStatus.valueOf(studentDto.status))
    )
}