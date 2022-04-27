package com.example

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import io.ktor.client.*
import io.ktor.client.request.*

fun main() {
    runBlocking {
        val client = HttpClient()

        val google = async { client.get<String>("https://www.google.com/") }
        val yandex = async { client.get<String>("https://yandex.ru/") }

        println(google.await())
        println(yandex.await())

        client.close()
    }
}
