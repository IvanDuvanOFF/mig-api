package org.example.migapi.repository

import org.example.migapi.domain.model.Country
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, String> {
}