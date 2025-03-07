package com.example.usermanagement.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {

    fun createUser(user: User): Mono<User> = userRepository.save(user)

    fun getUsers(): Flux<User> = userRepository.findAll()

    fun getUserById(id: UUID): Mono<User> = userRepository.findById(id)

    fun updateUser(id: UUID, user: User): Mono<User> {
        return userRepository.findById(id)
            .flatMap { existingUser ->
                userRepository.save(
                    existingUser.copy(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        birthDate = user.birthDate,
                        email = user.email
                    )
                )
            }
    }

    fun deleteUser(id: UUID): Mono<Void> = userRepository.deleteById(id)
}