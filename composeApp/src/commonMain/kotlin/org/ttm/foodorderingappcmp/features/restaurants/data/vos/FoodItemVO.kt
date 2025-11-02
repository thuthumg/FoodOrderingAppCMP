package org.ttm.foodorderingappcmp.features.restaurants.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity("cart")
@Serializable
data class FoodItemVO(

    @PrimaryKey(autoGenerate = false)
    @SerialName("id")
    val id : Long,

    @ColumnInfo("name")
    @SerialName("name")
    val name: String,

    @ColumnInfo("image_url")
    @SerialName("image_url")
    val imageUrl: String,

    @ColumnInfo("description")
    @SerialName("description")
    val description: String,


    @ColumnInfo("price")
    @SerialName("price")
    val price: Double,

    @ColumnInfo("created_at")
    @SerialName("created_at")
    val createdAt: String,

    @ColumnInfo("updated_at")
    @SerialName("updated_at")
    val updatedAt: String,

    @ColumnInfo("quantity")
    val qty: Int = 0
){
    fun getItemPrice(): Double {
        return price * qty
    }
}
