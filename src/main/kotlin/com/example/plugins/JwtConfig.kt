package com.example.plugins

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.auth.*
import java.util.*

object JwtConfig {
    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
    private const val issuer = "ktor.io"
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()


    fun makeToken(user: User): String = JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .sign(algorithm)
}