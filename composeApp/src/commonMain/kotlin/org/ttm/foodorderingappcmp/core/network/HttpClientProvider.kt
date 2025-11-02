package org.ttm.foodorderingappcmp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ttm.foodorderingappcmp.core.utils.URL
import kotlin.time.Duration.Companion.seconds

object HttpClientProvider {

    val httpClient by lazy{
        HttpClient{
            //Json Serialization
            install(ContentNegotiation){
                //Configuration
                json(
                    Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                        prettyPrint = true
                    }
                )
            }

            //Default Request
            install(DefaultRequest){
                url(URL)

                header("Accept","application/json")
                header("Content-Type","application/json")
            }

            //Time out
            install(HttpTimeout){
                connectTimeoutMillis = 30.seconds.inWholeMilliseconds
                socketTimeoutMillis = 30.seconds.inWholeMilliseconds
                requestTimeoutMillis = 30.seconds.inWholeMilliseconds
            }

            //logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("KTOR LOG: $message")
                    }
                }
                level = LogLevel.BODY  // or HEADERS, BODY, INFO
            }


        }


    }
}