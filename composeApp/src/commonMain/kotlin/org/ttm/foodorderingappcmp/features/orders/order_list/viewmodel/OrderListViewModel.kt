package org.ttm.foodorderingappcmp.features.orders.order_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.features.orders.data.repository.OrderListRepository
import org.ttm.foodorderingappcmp.features.orders.order_list.actions.OrderListActions
import org.ttm.foodorderingappcmp.features.orders.order_list.state.OrderListState

class OrderListViewModel : ViewModel() {

    val orderListRepository = OrderListRepository

    private val _state = MutableStateFlow(OrderListState())

    val orderListState = _state.asStateFlow()


    init {
        getAllOrderList()
    }

    fun getAllOrderList() {
        viewModelScope.launch {

            _state.update { it.copy(loading = true,

                message = "") }


            orderListRepository.getOrdersForUser(
                onSuccess = { orderList ->
                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                            submittedOrderItems = orderList ?: listOf()
                        )
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


    fun onAction(actions: OrderListActions){
        when(actions){
            is OrderListActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",

                        )
                }
            }
            is OrderListActions.OnTapItem -> {

            }
        }
    }


}
