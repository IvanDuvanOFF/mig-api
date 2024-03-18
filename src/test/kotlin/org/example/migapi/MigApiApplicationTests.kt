package org.example.migapi

import org.example.migapi.domain.service.security.TotpService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MigApiApplicationTests(
	@Autowired
	private val totpService: TotpService
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun isCodeRandom() {
		val secret = totpService.generateSecret()

		println("SECRET = $secret")

		val firstCode = totpService.generateCode(secret)

		println("FIRST CODE = $firstCode")

		val secendCode = totpService.generateCode(secret)

		println("SECOND CODE = $secendCode")
	}
}
