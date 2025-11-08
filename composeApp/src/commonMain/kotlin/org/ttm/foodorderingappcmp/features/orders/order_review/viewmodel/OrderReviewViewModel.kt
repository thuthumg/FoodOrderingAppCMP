package org.ttm.foodorderingappcmp.features.orders.order_review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.orders.data.repository.CartRepository
import org.ttm.foodorderingappcmp.features.orders.data.repository.OrderReviewRepository
import org.ttm.foodorderingappcmp.features.orders.order_review.state.OrderReviewState
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

class OrderReviewViewModel: ViewModel() {

    val cartRepository = CartRepository
    val orderReviewRepository = OrderReviewRepository

    private val _state = MutableStateFlow(OrderReviewState())

    val orderReviewState = _state.onStart {
        _state.update {
            it.copy(
                orderSubmitStatus = false
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(100L),
        _state.value
    )

    init {
        getAllCartFromDb()
        getDeliveryAddressAndPaymentFromDb()

    }

    fun getAllCartFromDb(){
        viewModelScope.launch {
            _state.update { it.copy(loading = true, errorDialogShowStatus = false) }

            when(val result = cartRepository.getAllCartFromDb()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success -> _state.update {
                    it.copy(
                        shoppingCartList =  result.data,
                        loading = false,
                        message = "",
                        errorDialogShowStatus = false
                    )
                }
            }

        }
    }

    fun getDeliveryAddressAndPaymentFromDb(){
        viewModelScope.launch {

            _state.update { it.copy(loading = true, errorDialogShowStatus = false) }

            when(val result =   orderReviewRepository.getDeliveryAddressAndPaymentFromDb()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success -> _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        errorDialogShowStatus = false,
                        deliveryAddressAndPaymentVO = result.data
                    )
                }
            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(loading = false, errorDialogShowStatus = false)
        }
    }

    fun submitOrder(paymentId: Long, deliveryAddressId: Long, foodItemList: List<FoodItemVO>) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, errorDialogShowStatus = false) }
            when(val result =  orderReviewRepository.submitOrder(paymentId,deliveryAddressId,foodItemList)){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        orderSubmitStatus = false,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success ->{

                    launch {cartRepository.deleteAllCart() }

                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                            orderSubmitStatus = true,
                            errorDialogShowStatus = false
                        )
                    }
                }
            }
        }
    }
}