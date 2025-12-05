package org.ttm.foodorderingappcmp.features.orders.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.features.orders.cart.actions.CartActions
import org.ttm.foodorderingappcmp.features.orders.cart.events.CartEvents
import org.ttm.foodorderingappcmp.features.orders.cart.events.CartEvents.*
import org.ttm.foodorderingappcmp.features.orders.cart.state.CartState
import org.ttm.foodorderingappcmp.features.orders.data.repository.CartRepository
import org.ttm.foodorderingappcmp.features.orders.data.repository.CheckoutRepository
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

class CartViewModel : ViewModel() {


    val cartRepository = CartRepository

    val checkoutRepository = CheckoutRepository

    private val _state = MutableStateFlow(CartState())

    val cartState = _state.asStateFlow()

    private val _navigationSharedFlow = MutableSharedFlow<CartEvents>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    init {
        getAllCartList()

    }

    fun getAllCartList() {
        viewModelScope.launch {

            _state.update { it.copy(loading = true, message = "", loginStatus = false) }


            cartRepository.getAllCartFromDbFlow().collect { cardList ->
                _state.update {
                    it.copy(
                        foodItemList = cardList ?: listOf(),
                        loading = false,
                        message = "",
                        loginStatus = false
                    )
                }
            }

//            when (val result = cartRepository.getAllCartFromDbFlow()) {
//                is FoodOrderingResult.Failure -> {
//                    if (result.message == "Unauthorized (401)") {
//                        _state.update {
//                            it.copy(
//                                loading = false,
//                                message = result.message,
//                                loginStatus = true
//                            )
//                        }
//                    } else {
//                        _state.update {
//                            it.copy(
//                                loading = false,
//                                message = result.message,
//                                loginStatus = false
//                            )
//                        }
//                    }
//                }
//
//                is FoodOrderingResult.Success -> {
//                    result.data.collect { cardList ->
//                        _state.update {
//                            it.copy(
//                                foodItemList = cardList ?: listOf(),
//                                loading = false,
//                                message = "",
//                                loginStatus = false
//                            )
//                        }
//                    }
//
//                }
//            }
        }
    }


    private fun onDecreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, loginStatus = false, message = "") }

            if ((foodItemVO.quantity ?: 0) >= 1) {
                cartRepository.insertCart(foodItemVO)
                // getAllCartList()
            } else {
                _state.update {
                    it.copy(
                        message = "",
                        loading = false,
                        showRemoveItemDialog = true,
                        removeItem = foodItemVO
                    )
                }
            }
        }
    }

    private fun deleteCart(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = false,
                    showRemoveItemDialog = false,
                    message = ""
                )
            }
            cartRepository.deleteCart(foodItemVO)
            //  getAllCartList()

        }
    }

    private fun onIncreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true,
                    loginStatus = false,
                    message = ""
                )
            }

            cartRepository.insertCart(foodItemVO)
            // getAllCartList()
        }
    }


    private fun getDeliveryAddressesAndPaymentMethods() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    loading = true,
                    message = "",
                    loginStatus = false
                )
            }

            cartRepository.getDeliveryAddressesAndPaymentMethods(onSuccess = { listVO ->

                if (listVO.deliveryAddresses.isNotEmpty() && listVO.paymentMethods.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                            showDeliveryPaymentDialog = true,
                            deliveryAddressAndPaymentListVO = listVO
                        )
                    }

                } else {

                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                            showDeliveryPaymentDialog = false,
                            deliveryAddressAndPaymentListVO = null
                        )
                    }

                    launch {
                        _navigationSharedFlow.emit(OnNavigateToCheckOut())
                    }

                }
            }, onFailure = {message,type ->
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
    private fun onTapConfirm(deliveryAddressVO: DeliveryAddressVO, paymentVO: PaymentVO) {

        viewModelScope.launch {

            checkoutRepository.deleteAllDeliveryAddressAndPayment()
            checkoutRepository.insertDeliveryAddressAndPayment(
                DeliveryAddressAndPaymentVO(
                    deliveryAddress = deliveryAddressVO,
                    paymentMethod = paymentVO
                )
            )

            _state.update {
                it.copy(
                    loading = false,
                    showDeliveryPaymentDialog = false,
                    deliveryAddressAndPaymentListVO = null,
                    message = ""
                )
            }



            launch {
                _navigationSharedFlow.emit(OnNavigateToReviewOrder())

            }


        }
    }

    fun onAction(action: CartActions) {
        when (action) {
            is CartActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToDetail())
                }
            }

            is CartActions.OnTapPlaceOrder -> {
                getDeliveryAddressesAndPaymentMethods()
            }

            is CartActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(message = "")
                }
            }

            is CartActions.OnUnauthorizedDialogDismissed -> {
                _state.update {
                    it.copy(message = "", loginStatus = false)
                }

                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToLogin())
                }
            }

            is CartActions.OnTapConfirm -> {
                onTapConfirm(
                    deliveryAddressVO = action.deliveryAddressVO,
                    paymentVO = action.paymentVO
                )
            }

            is CartActions.OnTapOrder -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToHome())
                }
            }

            is CartActions.OnTapAddNew -> {

                viewModelScope.launch {
                        _navigationSharedFlow.emit(OnNavigateToCheckOut())

                    _state.update {
                        it.copy(
                            loading = false,
                            showDeliveryPaymentDialog = false,
                            deliveryAddressAndPaymentListVO = null,
                            message = ""
                        )
                    }


                }
            }

            is CartActions.OnTapDecreaseBtn -> {
                onDecreaseItemQty(action.foodItemVO)
            }

            is CartActions.OnTapIncreaseBtn -> {
                onIncreaseItemQty(action.foodItemVO)

            }

            is CartActions.OnRemoveItemDialogDismissed -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            showRemoveItemDialog = false,
                            message = ""
                        )
                    }
                }

            }

            is CartActions.OnDeliveryPaymentDialogDismissed -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            showDeliveryPaymentDialog = false,
                            deliveryAddressAndPaymentListVO = null,
                            message = ""
                        )
                    }
                }
            }

            is CartActions.OnTapDeleteCart -> {
                deleteCart(action.foodItemVO)
            }
        }
    }
}