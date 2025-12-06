package org.ttm.foodorderingappcmp.features.restaurants.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO
import org.ttm.foodorderingappcmp.features.restaurants.network.api_service.RestaurantApiService
import org.ttm.foodorderingappcmp.features.restaurants.network.impls.RestaurantApiServiceImpl

object RestaurantRepository {

    val restaurantApiService: RestaurantApiService = RestaurantApiServiceImpl

    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getAllRestaurants(
        onSuccess: (List<RestaurantVO>) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit,
    ) = withContext(Dispatchers.IO) {
        restaurantApiService.getAllRestaurantList()
            .onSuccess {
                onSuccess(it ?: listOf())
            }.onError { error ->
                onFailure(error.error, error.errorType)
            }
    }


    suspend fun getRestaurantDetails(restaurantId: Long,
    ):FoodOrderingResult<RestaurantVO, FoodOrderingError> =
        withContext(Dispatchers.IO) {
            restaurantApiService.getRestaurantDetails(restaurantId)


        }

    suspend fun getAllCartFromDb(): List<FoodItemVO> {
        return appDatabase.cartDao().getAllCart()
    }

    suspend fun insertShoppingCart(foodItemVO: FoodItemVO) {
        appDatabase.cartDao().insertCart(foodItemVO)
    }

}