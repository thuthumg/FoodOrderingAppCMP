package org.ttm.foodorderingappcmp.features.restaurants.detail.actions

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

sealed class DetailActions {

    class OnTapBack: DetailActions()
    data class OnTapAdd(val foodItem: FoodItemVO) : DetailActions()
    class OnTapViewMyCart: DetailActions()
    data class OnTapCategoryTab(val selectedTab: Int): DetailActions()

    class OnErrorDialogDismissed: DetailActions()
    class OnUnauthorized: DetailActions()

}