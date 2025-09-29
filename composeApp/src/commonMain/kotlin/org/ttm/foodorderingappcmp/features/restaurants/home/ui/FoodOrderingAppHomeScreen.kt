package org.ttm.foodorderingappcmp.features.restaurants.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.italiano_bistro
import foodorderingappcmp.composeapp.generated.resources.napoli_pizzeria
import foodorderingappcmp.composeapp.generated.resources.pizza_palace
import foodorderingappcmp.composeapp.generated.resources.roma_trattoria
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeTopAppBar

@Composable
fun FoodOrderingAppHomeScreen(modifier: Modifier,onTapOrder : (Int) -> Unit) {

    val restaurantList = listOf(
        RestaurantItem(
            image = painterResource(Res.drawable.pizza_palace),
            name = "Pizza Palace",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.italiano_bistro),
            name = "Italiano Bistro",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.roma_trattoria),
            name = "Roma Trattoria",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.napoli_pizzeria),
            name = "Napoli Pizzeria",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),

        RestaurantItem(
            image = painterResource(Res.drawable.pizza_palace),
            name = "Pizza Palace",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.italiano_bistro),
            name = "Italiano Bistro",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.roma_trattoria),
            name = "Roma Trattoria",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.napoli_pizzeria),
            name = "Napoli Pizzeria",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.pizza_palace),
            name = "Pizza Palace",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.italiano_bistro),
            name = "Italiano Bistro",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.roma_trattoria),
            name = "Roma Trattoria",
            mealCategories = "Dinner,Lunch",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
        RestaurantItem(
            image = painterResource(Res.drawable.napoli_pizzeria),
            name = "Napoli Pizzeria",
            mealCategories = "Italian Food",
            reviewData = "4.8 ⭐\uFE0F  (3,300+)"
        ),
    )
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            HomeTopAppBar()
        },
        modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(MARGIN_CARD_MEDIUM_2),
            contentPadding = PaddingValues(bottom = 88.dp)
        ){
            items(restaurantList.size){
                RestaurantItemSection(restaurantList = restaurantList,
                    index = it,
                    onTapOrder = { restaurantId ->
                        onTapOrder(restaurantId)

                    })
            }
        }
    }



}



@Preview
@Composable
fun FoodOrderingAppHomeScreenPreview() {
    FoodOrderingAppHomeScreen(modifier = Modifier, onTapOrder = {})
}