package org.example.migapi.domain.service.security

import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow


@Component
class SimpleTotpService : TotpService {
    private val secureRandom = SecureRandom()
    private val baseEncoder = Base64.getEncoder()

    companion object {
        private const val HMAC_ALGO = "HmacSHA1"
        private const val PASSWORD_LENGTH = 6
        private const val TIME_STEP_SECONDS = 30
    }

    override fun generateSecret(): String = baseEncoder.encodeToString(secureRandom.generateSeed(24))

    override fun generateCode(secret: String): String {
        val secretBytes = Base64.getDecoder().decode(secret)
        val keySpec = SecretKeySpec(secretBytes, HMAC_ALGO)

        val timestamp = Instant.now().epochSecond / TIME_STEP_SECONDS
        val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
        buffer.putLong(timestamp)
        val timestampBytes: ByteArray = buffer.array()

        val mac = Mac.getInstance(HMAC_ALGO)
        mac.init(keySpec)
        val hash = mac.doFinal(timestampBytes)

        val offset = hash[hash.size - 1].toInt() and 0xf
        val binary =
            ((hash[offset].toInt() and 0x7f) shl 24) or ((hash[offset + 1].toInt() and 0xff) shl 16) or ((hash[offset + 2].toInt() and 0xff) shl 8) or (hash[offset + 3].toInt() and 0xff)

        val otp = binary % 10.0.pow(PASSWORD_LENGTH.toDouble()).toInt()

        return java.lang.String.format("%0$PASSWORD_LENGTH" + "d", otp)
    }

    override fun validateCode(otp: String, secret: String): Boolean {
        val generated = generateCode(secret)
        return generated == otp
    }
}