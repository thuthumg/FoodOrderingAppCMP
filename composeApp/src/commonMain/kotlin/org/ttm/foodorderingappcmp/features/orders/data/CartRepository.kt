package org.ttm.foodorderingappcmp.features.orders.data

import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

object CartRepository {

    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getAllCartFromDb(): Resource<List<FoodItemVO>>  =

        try {

            val responseData = appDatabase.cartDao().getAllCart()

            Resource.Success(responseData)

        } catch (e: Exception) {
            Resource.Error(e.message  ?: "Something went wrong!")
        }

    suspend fun insertCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().insertCart(foodItemVO)
    }

    suspend fun deleteCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().deleteCart(foodItemVO.id)
    }



}