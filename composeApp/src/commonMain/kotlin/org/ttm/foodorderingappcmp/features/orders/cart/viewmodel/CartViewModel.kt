package org.ttm.foodorderingappcmp.features.orders.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.orders.cart.state.CartState
import org.ttm.foodorderingappcmp.features.orders.data.repository.CartRepository
import org.ttm.foodorderingappcmp.features.orders.data.repository.CheckoutRepository
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

class CartViewModel: ViewModel() {


    val cartRepository = CartRepository

    val checkoutRepository = CheckoutRepository

    private val _state = MutableStateFlow(CartState())

    val cartState = _state.asStateFlow()

    init {
        getAllCartList()

    }
    fun getAllCartList(){
        viewModelScope.launch {

            _state.update { it.copy(loading = true, errorDialogShowStatus = false, message = "") }

            when(val result = cartRepository.getAllCartFromDbFlow()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success ->{
                    result.data.collect { cardList ->
                        _state.update {
                            it.copy(
                                foodItemList =  cardList ?: listOf(),
                                loading = false,
                                message = "",
                                successStatus = true,
                                errorDialogShowStatus = false
                            )
                        }
                    }

                }
            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(loading = false, errorDialogShowStatus = false, message = "")
        }
    }

    fun onDecreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, errorDialogShowStatus = false, message = "") }

            if ((foodItemVO.quantity ?: 0)>= 1) {
                cartRepository.insertCart(foodItemVO)
               // getAllCartList()
            } else {
                _state.update {
                    it.copy(
                        message = "",
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
                showRemoveItemDialog = false,
                message = "")
        }
    }

    fun deleteCart(foodItemVO: FoodItemVO){
        viewModelScope.launch {
            _state.update {
                it.copy(loading = false,
                    showRemoveItemDialog = false,
                    message = "")
            }
            cartRepository.deleteCart(foodItemVO)
          //  getAllCartList()

        }
    }

    fun onIncreaseItemQty(foodItemVO: FoodItemVO) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true,
                errorDialogShowStatus = false,
                message = "") }

            cartRepository.insertCart(foodItemVO)
           // getAllCartList()
        }
    }


    fun getDeliveryAddressesAndPaymentMethods(){
        viewModelScope.launch {

            _state.update { it.copy(loading = true,
                errorDialogShowStatus = false,
                message = ""
            )
            }

            when(val result = cartRepository.getDeliveryAddressesAndPaymentMethods()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        errorDialogShowStatus = true,
                        deliveryAddressAndPaymentListVO = null
                    )
                }
                is Resource.Success ->{
                    if(result.data.deliveryAddresses.isNotEmpty() && result.data.paymentMethods.isNotEmpty()){
                        _state.update {
                            it.copy(
                                loading = false,
                                message = "",
                                successStatus = true,
                                errorDialogShowStatus = false,
                                showDeliveryPaymentDialog = true,
                                deliveryAddressAndPaymentListVO = result.data
                            )
                        }
                    }else{
                        _state.update {
                            it.copy(
                                loading = false,
                                message = "",
                                successStatus = true,
                                errorDialogShowStatus = false,
                                showDeliveryPaymentDialog = false,
                                deliveryAddressAndPaymentListVO = result.data
                            )
                        }
                    }

                }
            }
        }
    }
    fun onDismissDeliveryPaymentDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = false,
                    showDeliveryPaymentDialog = null,
                    deliveryAddressAndPaymentListVO = null,
                    message = "")
            }
        }

    }

    fun onTapConfirm(deliveryAddressVO: DeliveryAddressVO,paymentVO: PaymentVO){

        viewModelScope.launch {
                checkoutRepository.deleteAllDeliveryAddressAndPayment()
                checkoutRepository.insertDeliveryAddressAndPayment(
                    DeliveryAddressAndPaymentVO(
                    deliveryAddress = deliveryAddressVO,
                    paymentMethod = paymentVO
                    )
                )

            _state.update {
                it.copy(loading = false,
                    showDeliveryPaymentDialog = null,
                    deliveryAddressAndPaymentListVO = null,
                    message = "")
            }



        }


    }


}