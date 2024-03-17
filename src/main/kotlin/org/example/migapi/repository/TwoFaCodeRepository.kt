package org.example.migapi.repository

import org.example.migapi.domain.model.entity.TwoFaCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TwoFaCodeRepository : JpaRepository<TwoFaCode, String> {
}