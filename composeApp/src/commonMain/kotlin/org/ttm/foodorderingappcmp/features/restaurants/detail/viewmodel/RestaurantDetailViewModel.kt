package org.ttm.foodorderingappcmp.features.restaurants.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.restaurants.data.repository.RestaurantRepository
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO
import org.ttm.foodorderingappcmp.features.restaurants.detail.actions.DetailActions
import org.ttm.foodorderingappcmp.features.restaurants.detail.events.DetailEvents
import org.ttm.foodorderingappcmp.features.restaurants.detail.state.RestaurantDetailState

class RestaurantDetailViewModel(val restaurantId: Long) : ViewModel(){

    val restaurantRepository = RestaurantRepository

    private val _state = MutableStateFlow(RestaurantDetailState())

    val restaurantDetailState = _state.onStart{

        val cartItems = getAllShoppingCartFromDb()

        _state.value.restaurantVO?.let { restaurantVO ->
            val updatedRestaurant =
                if (cartItems.isNotEmpty()){
                    updateQtyInRestaurant(restaurantVO, cartItems)
                }
                else
                { //reset quantity zero
                    restaurantVO.copy(
                        foodCategories = restaurantVO.foodCategories?.map { foodCategory ->
                            foodCategory.copy(
                                foodItems = foodCategory.foodItems.map { item ->
                                    item.copy(quantity = 0)
                                }
                            )
                        }
                    )
                }

            _state.update {
                it.copy(
                    restaurantVO = updatedRestaurant,
                    loading = false,
                    message = "",
                    showViewMyCart = cartItems.isNotEmpty()
                )
            }
        }


    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(100L),
            _state.value
        )

    private val _navigationSharedFlow : MutableSharedFlow<DetailEvents> = MutableSharedFlow()

    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    init {

        viewModelScope.launch {
            getRestaurantDetails()
        }

    }

    fun getRestaurantDetails() = viewModelScope.launch {

        _state.update { it.copy(
            loading = true,
            message = "") }

        try {
            supervisorScope {
                // Run BOTH in parallel on IO
                val restaurantDeferred = async(Dispatchers.IO) {
                    restaurantRepository.getRestaurantDetails(restaurantId) // Resource<RestaurantVO>
                }
                val cartDeferred = async(Dispatchers.IO) {
                    getAllShoppingCartFromDb() // List<CartItemVO>
                }

                when (val result = restaurantDeferred.await()) {
                    is Resource.Success -> {
                        val cartItems = try { cartDeferred.await() } catch (_: Exception) { emptyList() }
                        val updatedRestaurant =
                            if (cartItems.isNotEmpty())
                                updateQtyInRestaurant(result.data, cartItems)
                            else
                                result.data

                        _state.update {
                            it.copy(
                                restaurantVO = updatedRestaurant,
                                loading = false,
                                message = "",
                                showViewMyCart = cartItems.isNotEmpty()
                            )
                        }
                    }
                    is Resource.Error -> {
                        cartDeferred.cancel()
                        if (result.message == "Unauthorized (401)"){
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = result.message,
                                    loginStatus = true
                                )
                            }
                        }else{
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = result.message,
                                    loginStatus = false
                                )
                            }
                        }
                    }

                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    loading = false,
                    message = e.message ?: "Something went wrong!",
                    showViewMyCart = false

                )
            }
        }
    }


    suspend fun getAllShoppingCartFromDb(): List<FoodItemVO>{
       return restaurantRepository.getAllCartFromDb()
    }

    fun updateQtyInRestaurant(
        restaurant: RestaurantVO,
        cartItems: List<FoodItemVO>
    ): RestaurantVO {
        val qtyMap = cartItems.associateBy({ it.id }, { it.quantity })

       // val qtyMap = cartItems.map { it.id to (it.qty ?: 0) }.toMap()

        val updatedCategories = restaurant.foodCategories?.map { category ->
            val updatedFoods = category.foodItems.map { food ->
                val newQty = qtyMap[food.id] ?: 0   // 0 or keep null if not in cart
                food.copy(quantity = newQty)
            }
            category.copy( foodItems =  updatedFoods)
        }

        return restaurant.copy(foodCategories = updatedCategories)
    }

    fun addToCart(foodItemVO: FoodItemVO){
        viewModelScope.launch {
         restaurantRepository.insertShoppingCart(foodItemVO.copy(quantity = 1))
          getRestaurantDetails()
        }

    }


    fun onAction(action: DetailActions){
        when(action){

            is DetailActions.OnTapAdd -> {
                addToCart(foodItemVO = action.foodItem)
            }
            is DetailActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(DetailEvents.NavigateToHome())
                }
            }
            is DetailActions.OnTapCategoryTab -> {
                _state.update {
                    it.copy(
                        selectedTab = action.selectedTab
                    )
                }
            }
            is DetailActions.OnTapViewMyCart -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(DetailEvents.NavigateToCart())
                }
            }

            is DetailActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(message = "")
                }
            }

            is DetailActions.OnUnauthorized -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(DetailEvents.NavigateToLogin())
                }
            }
        }

    }

}