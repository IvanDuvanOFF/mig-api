package org.example.migapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "document_types")
data class DocumentType(
    @Id
    val name: String
) : Model
