package com.example.plugins

import io.ktor.auth.*

data class User(
    val id: Int,
    val name: String
) : Principal

interface UserSource {
    fun findUserById(id: Int): User

    fun findUserByCredentials(credential: UserPasswordCredential): User
}

class UserSourceImpl : UserSource {
    override fun findUserById(id: Int): User = users.getValue(id)

    override fun findUserByCredentials(credential: UserPasswordCredential): User = users.getValue(1)

    private val users = listOf(User(1, "TestUser")).associateBy(User::id)
}
