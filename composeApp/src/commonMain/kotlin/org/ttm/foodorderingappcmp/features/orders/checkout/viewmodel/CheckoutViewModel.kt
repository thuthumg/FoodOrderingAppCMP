package org.ttm.foodorderingappcmp.features.orders.checkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.features.orders.checkout.actions.CheckoutActions

import org.ttm.foodorderingappcmp.features.orders.checkout.events.CheckoutEvents
import org.ttm.foodorderingappcmp.features.orders.checkout.events.CheckoutEvents.*
import org.ttm.foodorderingappcmp.features.orders.checkout.state.CheckoutState
import org.ttm.foodorderingappcmp.features.orders.data.repository.CheckoutRepository

class CheckoutViewModel : ViewModel() {

    val checkoutRepository = CheckoutRepository

    private val _state = MutableStateFlow(CheckoutState())


    val state = _state.asStateFlow()
    private val _navigationSharedFlow = MutableSharedFlow<CheckoutEvents>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    fun addDeliveryAddressAndPayment() {

        val errorMessage = when {
            _state.value.cardNumber.isBlank() -> "Card Number is required.."
            _state.value.expiryDate.isBlank() -> "Expire Date is required."
            _state.value.cvv.isBlank() -> "CVV is required."
            _state.value.nameOnCard.isBlank() -> "Name on card is required."
            _state.value.deliveryAddress.isBlank() -> "Delivery address is required."
            !isValidCardNumber(_state.value.cardNumber) -> "Invalid card number"
            !isValidExpiryDate(_state.value.expiryDate) -> "Invalid expiry Date"
            !isValidCVV(_state.value.cvv) -> "Invalid CVV "
            else -> null
        }


        if (errorMessage != null) {
            _state.update {
                it.copy(
                    loading = false,
                    message = errorMessage
                )
            }
            return
        }

        viewModelScope.launch {

            _state.update { it.copy(loading = true,
                message = "") }

            checkoutRepository.addDeliveryAddressAndPayment(
                cardNumber = _state.value.cardNumber,
                expireDate = _state.value.expiryDate,
                cvv = _state.value.cvv,
                nameOnCard = _state.value.nameOnCard,
                deliveryAddress = _state.value.deliveryAddress,
                onSuccess = { deliveryAddressAndPaymentVO ->

                    _state.update {
                        it.copy(
                            deliveryAddressAndPaymentVO = deliveryAddressAndPaymentVO,
                            loading = false,
                            message = "",
                            loginStatus = false
                        )
                    }

                    launch {
                        checkoutRepository.insertDeliveryAddressAndPayment(
                            deliveryAddressAndPaymentVO
                        )
                    }
                    viewModelScope.launch {
                        _navigationSharedFlow.emit(CheckoutEvents.OnNavigateToOrderReview())
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



                }
            )


        }
    }

    fun isValidCardNumber(cardNumber: String): Boolean {
        val sanitized = cardNumber.replace(" ", "")
        return sanitized.length in 13..19 && sanitized.all { it.isDigit() }
    }

    fun isValidExpiryDate(expiry: String): Boolean {

        val regex = Regex("^(0[1-9]|1[0-2])/([0-9]{2})$")
        return regex.matches(expiry)
    }

    fun isValidCVV(cvv: String): Boolean {
        return cvv.matches(Regex("^[0-9]{3,4}$"))
    }

    fun onAction(actions: CheckoutActions){
        when(actions){
            is CheckoutActions.OnCardExpiryDateChanged -> {

                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            expiryDate = actions.expiryDate
                        )

                    }


            }
            is CheckoutActions.OnCardNameChanged -> {

                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            nameOnCard = actions.nameOnCard
                        )

                    }


            }
            is CheckoutActions.OnCardNumberChanged -> {

                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            cardNumber = actions.cardNumber
                        )

                    }


            }
            is CheckoutActions.OnCvvChanged -> {

                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            cvv = actions.cvv
                        )

                    }


            }
            is CheckoutActions.OnDeliveryAddressChanged -> {

                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            deliveryAddress = actions.deliveryAddress
                        )

                    }


            }
            is CheckoutActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToCart())
                }

            }
            is CheckoutActions.OnTapPlaceOrder -> {
                addDeliveryAddressAndPayment()
            }

            is CheckoutActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message ="",
                    )

                }
            }
        }

    }

}