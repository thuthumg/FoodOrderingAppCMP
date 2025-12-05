package org.ttm.foodorderingappcmp.features.orders.order_review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.features.orders.data.repository.CartRepository
import org.ttm.foodorderingappcmp.features.orders.data.repository.OrderReviewRepository
import org.ttm.foodorderingappcmp.features.orders.order_review.actions.OrderReviewActions
import org.ttm.foodorderingappcmp.features.orders.order_review.events.OrderReviewEvents
import org.ttm.foodorderingappcmp.features.orders.order_review.events.OrderReviewEvents.*
import org.ttm.foodorderingappcmp.features.orders.order_review.state.OrderReviewState
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

class OrderReviewViewModel: ViewModel() {

    val cartRepository = CartRepository
    val orderReviewRepository = OrderReviewRepository

    private val _state = MutableStateFlow(OrderReviewState())

    private val _navigationSharedFlow = MutableSharedFlow<OrderReviewEvents>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    val orderReviewState = _state.onStart {
        _state.update {
            it.copy(
                message = ""
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
            _state.update {
                it.copy(
                    shoppingCartList =  cartRepository.getAllCartFromDb(),
                    loading = false,
                    message = "",
                )
            }
        }
    }

    fun getDeliveryAddressAndPaymentFromDb(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = false,
                    message = "",
                    deliveryAddressAndPaymentVO = orderReviewRepository.getDeliveryAddressAndPaymentFromDb()
                )

            }


        }
    }


    fun submitOrder(paymentId: Long, deliveryAddressId: Long, foodItemList: List<FoodItemVO>) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true,

                message = "") }

            orderReviewRepository.submitOrder(paymentId,deliveryAddressId,foodItemList,
                onSuccess = {
                    launch {cartRepository.deleteAllCart() }

                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                        )
                    }
                    viewModelScope.launch {
                        _navigationSharedFlow.emit(OrderReviewEvents.OnNavigateToOrderConfirm())
                    }
                },
                onFailure = { message, type ->
                    when(type){

                        FoodOrderingErrorEnums.Remote.UNAUTHORIZED -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = message,
                                    loginStatus = true
                                )
                            }
                        }
                        else -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = message,
                                    loginStatus = false
                                )
                            }
                        }
                    }


                })


        }

    }



    fun onAction(actions: OrderReviewActions){
        when(actions){
            is OrderReviewActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToCart())
                }
            }
            is OrderReviewActions.OnTapConfirmOrder -> {
                submitOrder(actions.paymentId, actions.deliveryAddressId, actions.foodItemList)
            }

            is OrderReviewActions.OnErrorDialogDismissed ->{
                _state.update {
                    it.copy(
                        loading = false,
                        message = ""
                    )
                }
            }
        }
    }
}