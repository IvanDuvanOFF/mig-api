package org.example.migapi.domain.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "documents")
data class Document(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(targetEntity = DocumentType::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type")
    val documentType: DocumentType,

    val expirationDate: LocalDate,

    var link: String? = null,
) : Model
