package com.example.usermanagement.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import java.time.LocalDate

@Document(collection = "users")
data class User(
    @Id val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val email: String
)