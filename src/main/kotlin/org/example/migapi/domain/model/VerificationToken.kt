package org.example.migapi.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "verification_tokens")
data class VerificationToken(
    @Id
    val token: UUID = UUID.randomUUID(),

    @Column(name = "expiration_date")
    val expirationDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
)