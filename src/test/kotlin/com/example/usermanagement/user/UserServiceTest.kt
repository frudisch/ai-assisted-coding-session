package com.example.usermanagement.user

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import java.util.UUID
import java.time.LocalDate

class UserServiceTest {

    private val userRepository: UserRepository = mock()
    private val userService = UserService(userRepository)

    @Test
    fun testCreateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        whenever(userRepository.save(any())).thenReturn(Mono.just(user))
        val result = userService.createUser(user).block()
        assertEquals(user, result)
    }

    @Test
    fun testGetUserById() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        whenever(userRepository.findById(user.id)).thenReturn(Mono.just(user))
        val result = userService.getUserById(user.id).block()
        assertEquals(user, result)
    }

    @Test
    fun testUpdateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        val updatedUser = user.copy(firstName = "Jane")
        whenever(userRepository.findById(user.id)).thenReturn(Mono.just(user))
        whenever(userRepository.save(updatedUser)).thenReturn(Mono.just(updatedUser))
        val result = userService.updateUser(user.id, updatedUser).block()
        assertEquals(updatedUser, result)
    }

    @Test
    fun testDeleteUser() {
        val userId = UUID.randomUUID()
        whenever(userRepository.deleteById(userId)).thenReturn(Mono.empty())
        val result = userService.deleteUser(userId).block()
        assertEquals(null, result)
    }
}