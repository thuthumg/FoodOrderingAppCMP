package org.ttm.foodorderingappcmp.features.orders.cart.actions

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

sealed class CartActions {
    class OnTapBack: CartActions()
    class OnTapPlaceOrder: CartActions()

    data class OnTapIncreaseBtn(val foodItemVO: FoodItemVO): CartActions()
    data class OnTapDecreaseBtn(val foodItemVO: FoodItemVO): CartActions()
    data class OnTapConfirm(val deliveryAddressVO: DeliveryAddressVO,val paymentVO: PaymentVO): CartActions() // delivery address and payment alert
    class OnTapAddNew(): CartActions()
    class OnTapOrder: CartActions()
    class OnErrorDialogDismissed: CartActions()
    class OnUnauthorizedDialogDismissed: CartActions()
    class OnRemoveItemDialogDismissed : CartActions()
    class OnDeliveryPaymentDialogDismissed: CartActions()

    data class OnTapDeleteCart(val foodItemVO: FoodItemVO): CartActions()




}