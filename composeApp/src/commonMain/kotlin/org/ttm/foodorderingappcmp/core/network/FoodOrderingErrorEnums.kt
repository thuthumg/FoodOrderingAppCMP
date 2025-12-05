package org.ttm.foodorderingappcmp.core.network

sealed interface FoodOrderingErrorEnums {
    enum class Remote: FoodOrderingErrorEnums{
        SERVER_ERROR,
        NOT_FOUND,
        BAD_REQUEST,
        UNAUTHORIZED,
        SOCKET_TIMEOUT,
        FORBIDDEN,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        UNSUPPORTED_MEDIA_TYPE,
        BAD_GATEWAY,
        GATEWAY_TIMEOUT,
        NO_INTERNET,
        JSON_PARSE_ERROR,
        UNKNOWN,
        NETWORK_ERROR
    }

    enum class Local: FoodOrderingErrorEnums{
        DISK_FULL,
        UNKNOWN
    }
}