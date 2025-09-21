package org.ttm.foodorderingappcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform