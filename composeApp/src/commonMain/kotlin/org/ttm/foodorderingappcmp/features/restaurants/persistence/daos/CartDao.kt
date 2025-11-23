package org.ttm.foodorderingappcmp.features.restaurants.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(foodItemVO: FoodItemVO)

    @Query("SELECT * FROM cart")
    suspend fun getAllCart(): List<FoodItemVO>

    @Query("SELECT * FROM cart")
    fun getAllCartFromDbFlow(): Flow<List<FoodItemVO>?>

    @Query("DELETE FROM cart WHERE id = :foodItemId")
    suspend fun deleteCart(foodItemId: Long)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCart()

}