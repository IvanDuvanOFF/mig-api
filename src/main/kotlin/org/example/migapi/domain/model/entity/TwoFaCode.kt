package org.example.migapi.domain.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.random.Random

@Entity
@Table(name = "two_fa_codes")
data class TwoFaCode(
    @Id
    val code: String = genRandomCode(),

    @Column(name = "expiration_date")
    val expirationDate: LocalDateTime,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
) {
    companion object {
        private const val SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        private const val CAPACITY = 6;
        private val random = Random

        fun genRandomCode() =
            StringBuilder(CAPACITY).apply { repeat(CAPACITY) { this.append(SOURCE[random.nextInt(SOURCE.length)]) } }
                .toString()
    }
}
