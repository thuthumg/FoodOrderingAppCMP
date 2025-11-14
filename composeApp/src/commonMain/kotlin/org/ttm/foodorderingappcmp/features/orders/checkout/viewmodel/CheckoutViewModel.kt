package org.ttm.foodorderingappcmp.features.orders.checkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.orders.checkout.state.CheckoutState
import org.ttm.foodorderingappcmp.features.orders.data.repository.CheckoutRepository

class CheckoutViewModel : ViewModel() {

    val checkoutRepository = CheckoutRepository

    private val _state = MutableStateFlow(CheckoutState())


    val state = _state.asStateFlow()

    private val _onNavigateToOrderReview = MutableSharedFlow<Boolean>()

    val onNavigateToOrderReview = _onNavigateToOrderReview.asSharedFlow()

    fun addDeliveryAddressAndPayment(
        cardNumber: String,
        expireDate: String,
        cvv: String,
        nameOnCard: String,
        deliveryAddress: String,
    ) {

        val errorMessage = when {
            cardNumber.isBlank() -> "Card Number is required.."
            expireDate.isBlank() -> "Expire Date is required."
            cvv.isBlank() -> "CVV is required."
            nameOnCard.isBlank() -> "Name on card is required."
            deliveryAddress.isBlank() -> "Delivery address is required."
            !isValidCardNumber(cardNumber) -> "Invalid card number"
            !isValidExpiryDate(expireDate) -> "Invalid expiry Date"
            !isValidCVV(cvv) -> "Invalid CVV "
            else -> null
        }


        if (errorMessage != null) {
            _state.update {
                it.copy(
                    loading = false,
                    message = errorMessage,
                    checkoutApiStatus = false,
                    errorDialogShowStatus = true
                )
            }
            return
        }

        viewModelScope.launch {

            _state.update { it.copy(loading = true, errorDialogShowStatus = false, message = "") }


            when (val result = checkoutRepository.addDeliveryAddressAndPayment(
                cardNumber = cardNumber,
                expireDate = expireDate,
                cvv = cvv,
                nameOnCard = nameOnCard,
                deliveryAddress = deliveryAddress
            )) {
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        checkoutApiStatus = false,
                        errorDialogShowStatus = true,
                    )
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            deliveryAddressAndPaymentVO = result.data,
                            loading = false,
                            message = "",
                            checkoutApiStatus = true,
                            errorDialogShowStatus = false,
                        )
                    }

                    checkoutRepository.insertDeliveryAddressAndPayment(
                        result.data
                    )

                    onPlaceOrderHandled()
                }

            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(loading = false, errorDialogShowStatus = false, message = "")
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

    fun onPlaceOrderHandled() {
        viewModelScope.launch {
            _onNavigateToOrderReview.emit(true)
        }

    }
}