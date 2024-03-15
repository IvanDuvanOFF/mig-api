package org.example.migapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.migapi.domain.model.enums.EStudentStatus

@Entity
@Table(name = "student_statuses")
data class StudentStatus(
    @Id
    @Enumerated(value = EnumType.STRING)
    val name: EStudentStatus = EStudentStatus.NONE
) : Model
