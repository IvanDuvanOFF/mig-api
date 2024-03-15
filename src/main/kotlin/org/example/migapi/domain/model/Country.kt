package org.example.migapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "countries")
data class Country(
    @Id
    val name: String = "None"
) : Model
