package org.ttm.foodorderingappcmp.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json


suspend inline fun <reified T>transformResult(httpResponse:  HttpResponse): T{
    when(httpResponse.status.value){
        in 200..299 ->{
            return httpResponse.body<T>()
        }
        else -> {

            try {
                val errorResponse =  httpResponse.body<NetworkError>()
                val message = errorResponse.statusMessage
                    ?: errorResponse.message
                    ?: errorResponse.error
                    ?: "Request error (${httpResponse.status.value})"
                print("HttpClientExt $message")
                throw Exception(message)

            }catch (_: JsonConvertException){
                print("HttpClientExt JsonConvert ${httpResponse.bodyAsText()}")
                throw Exception(httpResponse.bodyAsText())
            }


        }
    }
}
