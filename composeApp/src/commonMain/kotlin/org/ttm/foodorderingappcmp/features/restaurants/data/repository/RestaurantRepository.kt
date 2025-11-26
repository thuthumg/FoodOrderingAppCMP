package org.ttm.foodorderingappcmp.features.restaurants.data.repository

import coil3.network.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.network.UnauthorizedException
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO
import org.ttm.foodorderingappcmp.features.restaurants.network.api_service.RestaurantApiService
import org.ttm.foodorderingappcmp.features.restaurants.network.impls.RestaurantApiServiceImpl

object RestaurantRepository {

    val restaurantApiService: RestaurantApiService = RestaurantApiServiceImpl

    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getAllRestaurants(): Resource<List<RestaurantVO>> =
        withContext(Dispatchers.IO) {
             try {
                 val response = restaurantApiService.getAllRestaurantList()
                 val restaurantVOList = response ?:
                 return@withContext Resource.Error("Empty list response")

                 Resource.Success(restaurantVOList)

             } catch (e: UnauthorizedException) {
                 Resource.Error(message = e.message)
             } catch (e: IOException) {
                 Resource.Error("Network error! Check your internet connection.")
             } catch (e: Exception) {
                 Resource.Error(e.message ?: "Something went wrong!")
             }

        }


//    suspend fun getAllRestaurants(): List<RestaurantVO>{
//
//        return withContext(Dispatchers.IO) {
//            val response = restaurantApiService.getAllRestaurantList()
//
//            return@withContext response ?: listOf()
//        }
//
//    }


    suspend fun getRestaurantDetails(restaurantId: Long): Resource<RestaurantVO> =
        withContext(Dispatchers.IO) {
            try {

                val responseData = restaurantApiService.getRestaurantDetails(restaurantId)

                Resource.Success(responseData)

            } catch (e: Exception) {
                Resource.Error(e.message  ?: "Something went wrong!")
            }

        }
//    suspend fun getRestaurantDetails(restaurantId: Long): RestaurantVO {
//
//        return withContext(Dispatchers.IO) {
//            val response = restaurantApiService.getRestaurantDetails(restaurantId)
//
//            return@withContext response
//        }
//
//    }

    suspend fun getAllCartFromDb(): List<FoodItemVO> {
       return appDatabase.cartDao().getAllCart()
    }

    suspend fun insertShoppingCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().insertCart(foodItemVO)

    }

}