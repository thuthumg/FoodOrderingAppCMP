package org.ttm.foodorderingappcmp.features.orders.order_list.state

import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

data class OrderListState(
    val submittedOrderItems: List<OrderItemVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    val errorDialogShowStatus: Boolean = false,
)
