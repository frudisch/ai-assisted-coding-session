package com.example.usermanagement.user

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Mono
import java.util.UUID
import java.time.LocalDate

@Testcontainers
@SpringBootTest
class UserRepositoryIntegrationTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var mongoTemplate: ReactiveMongoTemplate

    companion object {
        @Container
        val mongoDBContainer = MongoDBContainer("mongo:latest")

        @BeforeAll
        @JvmStatic
        fun startContainer() {
            mongoDBContainer.start()
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            mongoDBContainer.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }

    @Test
    fun testCreateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        val result = userRepository.save(user).block()
        assertEquals(user, result)
    }

    @Test
    fun testGetUserById() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        userRepository.save(user).block()
        val result = userRepository.findById(user.id).block()
        assertEquals(user, result)
    }

    @Test
    fun testUpdateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        userRepository.save(user).block()
        val updatedUser = user.copy(firstName = "Jane")
        val result = userRepository.save(updatedUser).block()
        assertEquals(updatedUser, result)
    }

    @Test
    fun testDeleteUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        userRepository.save(user).block()
        userRepository.deleteById(user.id).block()
        val result = userRepository.findById(user.id).block()
        assertEquals(null, result)
    }
}
