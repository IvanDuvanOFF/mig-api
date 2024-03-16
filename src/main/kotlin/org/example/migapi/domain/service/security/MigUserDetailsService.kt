package org.example.migapi.domain.service.security

import org.example.migapi.exception.UserNotFoundException
import org.example.migapi.exception.UsernameNullException
import org.example.migapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class MigUserDetailsService(
    @Autowired
    private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(exceptionClasses = [UsernameNullException::class, UserNotFoundException::class])
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw UsernameNullException("Username cannot be null")

        val userOptional = userRepository.findUserByUsername(username)

        if (userOptional.isEmpty)
            throw UserNotFoundException("User with username $username not found")

        val user = userOptional.get()

        return user.toSpringUser()
    }
}