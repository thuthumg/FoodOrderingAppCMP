package org.ttm.foodorderingappcmp.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException


suspend inline fun <reified T> safeApiCall(
     execute :  () -> HttpResponse
): FoodOrderingResult<T, FoodOrderingError> {
    return try {
        val response = execute()
        transformResult<T>(response)
    } catch (e: IOException) {
        FoodOrderingResult.Failure(
            FoodOrderingError(
                errorType = FoodOrderingErrorEnums.Remote.NETWORK_ERROR,
                error = "Network error: ${e.message}"
            )
        )
    } catch (e: Exception) {
        FoodOrderingResult.Failure(
            FoodOrderingError(
                errorType = FoodOrderingErrorEnums.Remote.UNKNOWN,
                error = "Unexpected error: ${e.message}"
            )
        )
    }
}



suspend inline fun <reified T>transformResult(httpResponse:  HttpResponse): FoodOrderingResult<T,FoodOrderingError>{
    when(httpResponse.status.value){
        in 200..299 ->{
           try {
               return FoodOrderingResult.Success(httpResponse.body<T>())
           }catch (e: SerializationException){
               return FoodOrderingResult.Failure(
                   FoodOrderingError(
                       errorType = FoodOrderingErrorEnums.Remote.JSON_PARSE_ERROR,
                       error = "Failed to parse successful response data: ${e.message}. Raw: ${httpResponse.bodyAsText()}"
                   )
               )
           }
            catch (e: Exception){
                return FoodOrderingResult.Failure(
                    FoodOrderingError(
                        errorType = FoodOrderingErrorEnums.Remote.UNKNOWN,
                        error = "An unexpected error occurred during success body processing: ${e.message}. Raw: ${httpResponse.bodyAsText()}"
                    )
                )
            }
        }

        //Client Errors
        400 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.BAD_REQUEST
                )
            )
        }

        401 -> {
          return FoodOrderingResult.Failure(
              responseToError(
                  httpResponse = httpResponse,
                  errorType = FoodOrderingErrorEnums.Remote.UNAUTHORIZED
              )
          )
        }
        403 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.FORBIDDEN
                )
            )
        }
        404 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.NOT_FOUND
                )
            )
        }

        408 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.REQUEST_TIMEOUT
                )
            )
        }
        415 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.UNSUPPORTED_MEDIA_TYPE
                )
            )
        }
        429 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.TOO_MANY_REQUESTS
                )
            )
        }

    //Server Errors
        500 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.SERVER_ERROR
                )
            )
        }
        502 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.BAD_GATEWAY
                )
            )
        }

        504 -> {
            return FoodOrderingResult.Failure(
                responseToError(
                    httpResponse = httpResponse,
                    errorType = FoodOrderingErrorEnums.Remote.GATEWAY_TIMEOUT
                )
            )
        }
        else -> {
            return FoodOrderingResult.Failure(
                FoodOrderingError(
                    errorType = FoodOrderingErrorEnums.Remote.SERVER_ERROR,
                    error = "Unknown error"
                )
            )

        }
    }
}

suspend inline fun responseToError(httpResponse: HttpResponse,
                                   errorType: FoodOrderingErrorEnums): FoodOrderingError{
    try {
        val error = httpResponse.body<FoodOrderingError>()
        error.errorType = errorType
        return error
    }catch (e: SerializationException){
        return FoodOrderingError(
            errorType = FoodOrderingErrorEnums.Remote.JSON_PARSE_ERROR,
            error = e.message ?: "Unknown error"
        )
    }catch (e: Exception){
        return FoodOrderingError(
            errorType = FoodOrderingErrorEnums.Remote.UNKNOWN,
            error = e.message ?: "Unknown error"
        )
    }
}
