package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long
) {
    companion object {
        val users: MutableMap<Long, User> = mutableMapOf()

        var id = 0L

        fun registerUser() = User(++id).also { users[id] = it }
    }
}
