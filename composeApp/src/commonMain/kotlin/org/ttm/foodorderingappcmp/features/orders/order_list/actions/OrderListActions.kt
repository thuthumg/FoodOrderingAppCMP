package org.ttm.foodorderingappcmp.features.orders.order_list.actions

sealed class OrderListActions{
    class  OnErrorDialogDismissed: OrderListActions()
    class OnTapItem: OrderListActions()
}