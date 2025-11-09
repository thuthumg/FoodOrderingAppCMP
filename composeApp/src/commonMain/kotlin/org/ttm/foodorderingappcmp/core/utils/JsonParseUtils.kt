package org.ttm.foodorderingappcmp.core.utils

import kotlinx.serialization.json.Json

val universalJsonParser = Json{
    ignoreUnknownKeys = true
    explicitNulls = false
    prettyPrint = true
}

val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")