package org.ttm.foodorderingappcmp.features.restaurants.network.api_service

import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO

interface RestaurantApiService {

    suspend fun getAllRestaurantList() : FoodOrderingResult<List<RestaurantVO>?, FoodOrderingError>

    suspend fun getRestaurantDetails(restaurantId: Long): FoodOrderingResult<RestaurantVO, FoodOrderingError>
}