package org.example.migapi.domain.model

import org.example.migapi.domain.dto.Dto

interface Model {
    fun toDto(): Any
}