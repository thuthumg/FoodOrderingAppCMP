package org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.ic_home
import foodorderingappcmp.composeapp.generated.resources.ic_orders
import foodorderingappcmp.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.BOTTOM_NAVIGATION_ICON_SIZE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.home.ui.FoodOrderingAppHomeScreen
import org.ttm.foodorderingappcmp.features.orders.order_list.ui.FoodOrderingAppOrdersScreen
import org.ttm.foodorderingappcmp.features.profile.setting.ui.FoodOrderingAppProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomNavigationScreen(onTapOrder: (Int)-> Unit) {

    val bottomNavigationItems = listOf(
        BottomNavigationItemData(name = "Home", icon = painterResource(Res.drawable.ic_home)),
        BottomNavigationItemData(name = "Orders", icon = painterResource(Res.drawable.ic_orders)),
        BottomNavigationItemData(name = "Profile", icon = painterResource(Res.drawable.ic_profile))
    )
//state
    var selectedItem by remember { mutableStateOf("Home") }
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            HomeTopAppBar()
        },
        bottomBar = {
            NavigationBar(
                containerColor = SCREEN_BG_COLOR
            ) {
                bottomNavigationItems.forEach {
                    NavigationBarItem(
                        selected = selectedItem == it.name,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TITLE_BLACK_COLOR,
                            selectedTextColor = TITLE_BLACK_COLOR,
                            unselectedIconColor = OUTLINE_TXT_FIELD_TXT_COLOR,
                            unselectedTextColor = OUTLINE_TXT_FIELD_TXT_COLOR,
                            indicatorColor = Color.Transparent
                        ),
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = null,
                                modifier = Modifier.size(BOTTOM_NAVIGATION_ICON_SIZE)
                            )
                        },
                        label = {
                            Text(
                                text = it.name,
                                fontSize = TEXT_SMALL
                            )
                        },
                        onClick = {
                            selectedItem = it.name
                        }
                    )
                }
            }

        },
    ) { innerPadding ->
       when(selectedItem){
           "Home" -> FoodOrderingAppHomeScreen(modifier = Modifier.padding(innerPadding),
               onTapOrder = {restaurantId -> onTapOrder(restaurantId)})
           "Orders" -> FoodOrderingAppOrdersScreen(modifier = Modifier.padding(innerPadding))
           "Profile" -> FoodOrderingAppProfileScreen(modifier = Modifier.padding(innerPadding))
       }
    }
}

data class BottomNavigationItemData(
    val name: String,
    val icon: Painter
)



@Preview
@Composable
fun HomeBottomNavigationScreenPreview() {
    HomeBottomNavigationScreen(onTapOrder = {})
}