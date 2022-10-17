@file:OptIn(KtorExperimentalLocationsAPI::class)

package ru.sejapoe.chess.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.User

fun Application.configureRouting() {
    install(Locations) {
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/registerMeToQueue") {
            call.respond(HttpStatusCode.OK, User.registerUser().also { Game.addUserToQueue(it) })
        }
        get("/matchmakingStatus/{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
            println(id)
            val user = User.users[id] ?: return@get call.respond(HttpStatusCode.NotFound)
            println(user)
            if (Game.matchmaking.contains(user)) return@get call.respond(HttpStatusCode.OK)
            val game = Game.games.values.find {
                it.player1 == user || it.player2 == user
            } ?: return@get call.respond(HttpStatusCode.InternalServerError)
            println(game)
            call.respond(HttpStatusCode.Created, game.id)
        }
        get("/game/{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val game = Game.games[id] ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(HttpStatusCode.OK, game.board)
        }
        post("/game/{id}/move") {
            val id = call.parameters["id"]?.toLong() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val game = Game.games[id] ?: return@post call.respond(HttpStatusCode.NotFound)

            try {
                game.applyMove(call.receive())
            } catch (e: Exception) {
                return@post call.respond(HttpStatusCode.BadRequest)
            }
            call.respond(HttpStatusCode.OK)
        }
        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }
    }
}

@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}
