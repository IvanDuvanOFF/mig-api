package org.example.migapi.domain.model

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.UUID

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
    var isActive: Boolean = false
) {
    fun toSpringUser() = SpringUser.builder()
        .username(username)
        .password(password)
        .authorities(
            listOf(SimpleGrantedAuthority(role.name))
        )
        .disabled(!isActive)
        .build()
}

typealias SpringUser = org.springframework.security.core.userdetails.User
