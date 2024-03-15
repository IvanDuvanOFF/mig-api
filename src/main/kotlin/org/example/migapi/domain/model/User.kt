package org.example.migapi.domain.model

import jakarta.persistence.*
import org.example.migapi.domain.dto.AdminDto
import org.example.migapi.domain.dto.Dto
import org.example.migapi.domain.dto.UserDto
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @ManyToOne(targetEntity = Role::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    var role: Role,

    @Column(nullable = false, name = "is_active")
    var isActive: Boolean = false,

    var name: String = "None",

    var surname: String = "None",

    var patronymic: String? = null,

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty
    var email: String = "None",

    @NotEmpty
    var phone: String = "None",

    @NotEmpty
    @ManyToOne(targetEntity = Country::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    var country: Country = Country(),

    @NotEmpty
    var birthday: LocalDate = LocalDate.EPOCH,

    @NotEmpty
    @ManyToOne(targetEntity = StudentStatus::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    var status: StudentStatus = StudentStatus()
) {
    fun toSpringUser(): UserDetails = SpringUser.builder()
        .username(username)
        .password(password)
        .authorities(
            listOf(SimpleGrantedAuthority(role.name.name))
        )
        .disabled(!isActive)
        .build()

    fun toUserDto() = UserDto(
        id = id.toString(),
        username = username,
        password = password,
        isActive = isActive,
        role = role.name.name
    )

    fun toAdminDto() = AdminDto(
        id = id.toString(),
        username = username,
        password = password,
        isActive = isActive,
        name = name,
        surname = surname
    )
}

typealias SpringUser = org.springframework.security.core.userdetails.User