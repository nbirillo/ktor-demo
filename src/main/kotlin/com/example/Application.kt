package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import java.text.DateFormat

fun Application.demo() {
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
        configureSecurity()
    }
    configureRouting()
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        demo()
    }.start(wait = true)
}
