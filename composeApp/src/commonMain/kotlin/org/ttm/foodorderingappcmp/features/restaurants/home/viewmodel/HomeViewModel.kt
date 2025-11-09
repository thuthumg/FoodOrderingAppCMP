package org.ttm.foodorderingappcmp.features.restaurants.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.restaurants.data.repository.RestaurantRepository
import org.ttm.foodorderingappcmp.features.restaurants.home.state.HomeState

class HomeViewModel: ViewModel() {

    val restaurantRepository = RestaurantRepository

    private val _state = MutableStateFlow(HomeState())

    val homeState = _state.asStateFlow()

    init {
        getAllRestaurants()
    }

    fun getAllRestaurants(){
        viewModelScope.launch {

            _state.update { it.copy(loading = true, errorDialogShowStatus = false, message = "") }

            when(val result = restaurantRepository.getAllRestaurants()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        errorDialogShowStatus = true
                    )
                }

                is Resource.Success -> _state.update {
                    it.copy(
                        restaurantList =  result.data,
                        loading = false,
                        message = "",
                        errorDialogShowStatus = false
                    )
                }
            }




        }
    }
    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(errorDialogShowStatus = false, loginStatus = false, message = "")
        }
    }
}