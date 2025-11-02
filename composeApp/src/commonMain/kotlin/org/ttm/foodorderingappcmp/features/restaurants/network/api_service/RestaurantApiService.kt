package org.ttm.foodorderingappcmp.features.restaurants.network.api_service

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO

interface RestaurantApiService {

    suspend fun getAllRestaurantList() : List<RestaurantVO>?

    suspend fun getRestaurantDetails(restaurantId: Long): RestaurantVO
}