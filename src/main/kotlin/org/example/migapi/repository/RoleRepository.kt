package org.example.migapi.repository

import org.example.migapi.domain.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String> {
}