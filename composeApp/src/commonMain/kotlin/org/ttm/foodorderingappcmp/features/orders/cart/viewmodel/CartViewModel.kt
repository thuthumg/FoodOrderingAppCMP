package org.ttm.foodorderingappcmp.features.orders.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.orders.cart.state.CartState
import org.ttm.foodorderingappcmp.features.orders.data.CartRepository
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

class CartViewModel: ViewModel() {


    val cartRepository = CartRepository

    private val _state = MutableStateFlow(CartState())

    val cartState = _state.asStateFlow()

    init {
        getAllCartList()

    }
    fun getAllCartList(){
        viewModelScope.launch {

            _state.update { it.copy(loading = true, dismissStatus = true) }

            when(val result = cartRepository.getAllCartFromDb()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        dismissStatus = false
                    )
                }
                is Resource.Success -> _state.update {
                    it.copy(
                        foodItemList =  result.data,
                        loading = false,
                        message = "",
                        successStatus = true,
                        dismissStatus = true
                    )
                }
                else -> Unit
            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(dismissStatus = true)
        }
    }

    fun onDecreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, dismissStatus = true) }

            if (foodItemVO.qty >= 1) {
                cartRepository.insertCart(foodItemVO)
                getAllCartList()
            } else {
                _state.update {
                    it.copy(
                        loading = false,
                        showRemoveItemDialog = true,
                        removeItem = foodItemVO)
                }
            }
        }
    }
    fun onDismissRemoveItemDialog() {
        _state.update {
            it.copy(loading = false,
                showRemoveItemDialog = false)
        }
    }

    fun deleteCart(foodItemVO: FoodItemVO){
        viewModelScope.launch {
            _state.update {
                it.copy(loading = false,
                    showRemoveItemDialog = false)
            }
            cartRepository.deleteCart(foodItemVO)
            getAllCartList()

        }
    }

    fun onIncreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, dismissStatus = true) }

            cartRepository.insertCart(foodItemVO)
            getAllCartList()
        }
    }


}