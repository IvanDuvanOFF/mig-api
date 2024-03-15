package org.example.migapi.domain.service.security

import org.example.migapi.domain.model.SpringUser
import org.example.migapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MigUserDetailsService(
    @Autowired
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw NullPointerException()

        val userOptional = userRepository.findUserByUsername(username)

        if (userOptional.isEmpty)
            throw NullPointerException()

        val user = userOptional.get()

        return user.toSpringUser()
    }
}