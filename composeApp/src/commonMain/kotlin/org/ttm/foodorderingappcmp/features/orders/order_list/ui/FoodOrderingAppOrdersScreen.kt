package org.ttm.foodorderingappcmp.features.orders.order_list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.ttm.foodorderingappcmp.common.ui.ErrorAlertDialog
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.ACTION_BAR_HEIGHT
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.features.orders.order_list.state.OrderListState
import org.ttm.foodorderingappcmp.features.orders.order_list.viewmodel.OrderListViewModel

@Composable
fun FoodOrderingAppOrdersScreenRoute(orderListViewModel: OrderListViewModel,
                                     onTapItem: () -> Unit) {

    val orderListState by orderListViewModel.orderListState.collectAsStateWithLifecycle()

    FoodOrderingAppOrdersScreen(
        orderListState = orderListState,
        onDismissErrorAlertDialog = {
            orderListViewModel.onDismissErrorAlertDialog()
        },
        onTapItem = {
            onTapItem()
        }
    )
}


@Composable
fun FoodOrderingAppOrdersScreen(
    orderListState: OrderListState,
    onDismissErrorAlertDialog:() -> Unit,
    onTapItem: ()-> Unit) {

    /************* Loading State *********************/
    if (orderListState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error State *********************/
    if (orderListState.message.isNotBlank() && (orderListState.errorDialogShowStatus)) {
        ErrorAlertDialog(
            title = "Error",
            message = orderListState.message,
            onDismiss = {
                onDismissErrorAlertDialog()
            }
        )
    }

    /***************** Order List Screen ************************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            OrderTopAppBar()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(
            top = innerPadding.calculateTopPadding(),
            bottom = (innerPadding.calculateBottomPadding())),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
            contentPadding = PaddingValues(bottom = ACTION_BAR_HEIGHT)){

            //order list
            items(orderListState.submittedOrderItems.size){ index ->
                SubmittedOrderItemRow(
                    orderItemVO = orderListState.submittedOrderItems[index],
                    onTapItem = onTapItem
                )
            }

        }

    }
}

//@Preview
//@Composable
//fun FoodOrderingAppOrdersScreenPreview() {
//    FoodOrderingAppOrdersScreen(modifier = Modifier, onTapItem = {})
//}