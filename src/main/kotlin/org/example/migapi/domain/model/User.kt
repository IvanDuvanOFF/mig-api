package org.example.migapi.domain.model

import jakarta.persistence.*
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
    var role: Role,

    @Column(nullable = false, name = "is_active")
    var isActive: Boolean = false
)
