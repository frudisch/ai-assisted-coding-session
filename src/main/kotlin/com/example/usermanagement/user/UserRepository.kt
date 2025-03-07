package com.example.usermanagement.user

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : ReactiveMongoRepository<User, UUID>