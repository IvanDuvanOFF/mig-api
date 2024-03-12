package org.example.migapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "typography_statuses")
data class TypographyStatus(
    @Id
    val name: String
)
