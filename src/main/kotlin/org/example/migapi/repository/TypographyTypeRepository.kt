package org.example.migapi.repository

import org.example.migapi.domain.model.TypographyType
import org.springframework.data.jpa.repository.JpaRepository

interface TypographyTypeRepository : JpaRepository<TypographyType, String> {
}