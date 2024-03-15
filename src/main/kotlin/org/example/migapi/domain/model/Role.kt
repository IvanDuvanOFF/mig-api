package org.example.migapi.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @Enumerated(EnumType.STRING)
    val name: ERole
)
