package com.example.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get

data class TestPostData(val id: Int, val message: String)

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        get("/queryParamsExample") {
            val params = call.request.queryParameters
            val id = params["id"]
            val response = if (id != null) {
                "Current id is $id"
            } else {
                "Param ID was not sent"
            }
            call.respondText(response)
        }

        post("/postQueryExample") {
            val testData = call.receive<TestPostData>()
            call.respondText("${testData.message}: ${testData.id}", ContentType.Text.Html)
        }

    }
}
