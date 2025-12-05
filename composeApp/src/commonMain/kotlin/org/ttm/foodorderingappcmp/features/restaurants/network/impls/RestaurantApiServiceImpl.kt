package org.ttm.foodorderingappcmp.features.restaurants.network.impls

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.HttpClientProvider
import org.ttm.foodorderingappcmp.core.network.safeApiCall
import org.ttm.foodorderingappcmp.core.network.transformResult
import org.ttm.foodorderingappcmp.core.utils.GET_ALL_RESTAURANTS
import org.ttm.foodorderingappcmp.core.utils.GET_RESTAURANT_DETAILS
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO
import org.ttm.foodorderingappcmp.features.restaurants.network.api_service.RestaurantApiService

object RestaurantApiServiceImpl : RestaurantApiService{
    override suspend fun getAllRestaurantList():  FoodOrderingResult<List<RestaurantVO>?, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.get(GET_ALL_RESTAURANTS){
                header(HttpHeaders.Authorization,"Bearer $apiToken")
            }
        }
    }

    override suspend fun getRestaurantDetails(restaurantId: Long): FoodOrderingResult<RestaurantVO, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.get("$GET_RESTAURANT_DETAILS${restaurantId}"){
                header(HttpHeaders.Authorization,"Bearer $apiToken")
            }
        }
    }
}