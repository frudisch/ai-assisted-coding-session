package com.example.usermanagement.user

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono
import java.util.UUID
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    @MockBean
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        RestAssured.port = port
    }

    @Test
    fun testCreateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        Mockito.`when`(userService.createUser(user)).thenReturn(Mono.just(user))
        Given {
            contentType(ContentType.JSON)
            body(user)
        } When {
            post("/api/users")
        } Then {
            statusCode(HttpStatus.CREATED.value())
            body("email", equalTo(user.email))
        }
    }

    @Test
    fun testGetUserById() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        Mockito.`when`(userService.getUserById(user.id)).thenReturn(Mono.just(user))
        Given {
            contentType(ContentType.JSON)
        } When {
            get("/api/users/${user.id}")
        } Then {
            statusCode(HttpStatus.OK.value())
            body("email", equalTo(user.email))
        }
    }

    @Test
    fun testUpdateUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        val updatedUser = user.copy(firstName = "Jane")
        Mockito.`when`(userService.updateUser(user.id, updatedUser)).thenReturn(Mono.just(updatedUser))
        Given {
            contentType(ContentType.JSON)
            body(updatedUser)
        } When {
            put("/api/users/${user.id}")
        } Then {
            statusCode(HttpStatus.OK.value())
            body("firstName", equalTo(updatedUser.firstName))
        }
    }

    @Test
    fun testDeleteUser() {
        val user = User(UUID.randomUUID(), "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")
        Mockito.`when`(userService.deleteUser(user.id)).thenReturn(Mono.empty())
        Given {
            contentType(ContentType.JSON)
        } When {
            delete("/api/users/${user.id}")
        } Then {
            statusCode(HttpStatus.NO_CONTENT.value())
        }
    }
}