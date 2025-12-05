package org.ttm.foodorderingappcmp.features.orders.checkout.actions

sealed class CheckoutActions{
    class OnTapBack: CheckoutActions()
    class OnTapPlaceOrder: CheckoutActions()
    class OnCardNumberChanged(val cardNumber: String): CheckoutActions()
    class OnCvvChanged(val cvv: String): CheckoutActions()
    class OnCardExpiryDateChanged(val expiryDate: String): CheckoutActions()
    class OnCardNameChanged(val nameOnCard: String): CheckoutActions()
    class OnDeliveryAddressChanged(val deliveryAddress: String): CheckoutActions()

    class  OnErrorDialogDismissed: CheckoutActions()
}