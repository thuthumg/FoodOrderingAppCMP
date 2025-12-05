package org.ttm.foodorderingappcmp.features.restaurants.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.features.restaurants.data.repository.RestaurantRepository
import org.ttm.foodorderingappcmp.features.restaurants.home.actions.HomeActions
import org.ttm.foodorderingappcmp.features.restaurants.home.events.HomeEvents
import org.ttm.foodorderingappcmp.features.restaurants.home.events.HomeEvents.*
import org.ttm.foodorderingappcmp.features.restaurants.home.state.HomeState

class HomeViewModel: ViewModel() {

    val restaurantRepository = RestaurantRepository

    private val _state = MutableStateFlow(HomeState())

    val homeState = _state.asStateFlow()

    private val _navigationSharedFlow : MutableSharedFlow<HomeEvents> = MutableSharedFlow()

    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    init {
        getAllRestaurants()
    }

    fun getAllRestaurants(){
        viewModelScope.launch {

            _state.update {
                it.copy(loading = true,
                message = "") }

            restaurantRepository.getAllRestaurants(
                onSuccess = { restaurantList ->
                    _state.update {
                        it.copy(
                            restaurantList =  restaurantList,
                            loading = false,
                            message = "",
                            loginStatus = false
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
    fun onAction(action: HomeActions){
        when(action){
            is HomeActions.OnTapOrder -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToRestaurantDetail(action.restaurantDetailId))
                }

            }
            is HomeActions.OnTapShoppingCart -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToCart())
                }

            }

            is HomeActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        loginStatus = false
                    )
                }
            }

            is HomeActions.OnUnauthorized -> {
               viewModelScope.launch {
                   _state.update {
                       it.copy(
                           loading = false,
                           message = "",
                           loginStatus = false
                       )
                   }
                   launch {
                       _navigationSharedFlow.emit(NavigateToLogin())
                   }
               }
            }
        }

    }
}