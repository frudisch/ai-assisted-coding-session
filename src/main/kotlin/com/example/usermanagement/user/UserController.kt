package com.example.usermanagement.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User): Mono<User> = userService.createUser(user)

    @GetMapping
    fun getUsers(): Flux<User> = userService.getUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): Mono<User> = userService.getUserById(id)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody user: User): Mono<User> = userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: UUID): Mono<Void> = userService.deleteUser(id)
}