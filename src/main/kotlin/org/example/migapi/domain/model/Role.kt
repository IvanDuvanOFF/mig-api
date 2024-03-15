package org.example.migapi.domain.model

import jakarta.persistence.*
import org.example.migapi.domain.model.enums.ERole

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @Enumerated(EnumType.STRING)
    val name: ERole
) : Model
