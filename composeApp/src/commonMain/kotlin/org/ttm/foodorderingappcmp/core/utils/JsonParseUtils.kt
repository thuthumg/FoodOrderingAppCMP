package org.ttm.foodorderingappcmp.core.utils

import kotlinx.serialization.json.Json

val universalJsonParser = Json{
    ignoreUnknownKeys = true
    explicitNulls = false
    prettyPrint = true
}