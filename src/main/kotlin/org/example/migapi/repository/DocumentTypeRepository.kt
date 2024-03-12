package org.example.migapi.repository

import org.example.migapi.domain.model.DocumentType
import org.springframework.data.jpa.repository.JpaRepository

interface DocumentTypeRepository : JpaRepository<DocumentType, String> {
}