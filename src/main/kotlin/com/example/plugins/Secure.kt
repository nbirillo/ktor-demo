package com.example.plugins

import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.http.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

val ApplicationCall.user get() = authentication.principal<User>()

fun Application.configureSecurity() {
    install(CallLogging)

    val userSource: UserSource = UserSourceImpl()
    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asInt()?.let(userSource::findUserById)
            }
        }
    }

    install(Routing) {

        post("login") {
            val credentials = call.receive<UserPasswordCredential>()
            val user = userSource.findUserByCredentials(credentials)
            val token = JwtConfig.makeToken(user)
            call.respondText(token)
        }

        authenticate {
            route("authUser") {
                get {
                    val user = call.user!!
                    call.respond("User with id:${user.id} has name ${user.name}")
                }
            }
        }

        // Authentication is optional
        authenticate(optional = true) {
            get("user") {
                val user = call.user
                if (user != null) {
                    call.response.status(HttpStatusCode.OK)
                    call.respond("User ${user.name} authenticated!")
                } else {
                    call.response.status(HttpStatusCode(418, "I'm a tea pot"))
                    call.respond("Oops, you should login")
                }
            }
        }
    }
}
