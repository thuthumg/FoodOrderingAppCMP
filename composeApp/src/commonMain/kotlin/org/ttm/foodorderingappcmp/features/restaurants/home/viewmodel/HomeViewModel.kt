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

        viewModelScope.launch {

            _state.update { it.copy(loading = true, dismissStatus = true) }

            when(val result = restaurantRepository.getAllRestaurants()){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        dismissStatus = false
                    )
                }
                Resource.Loading ->  _state.update { it.copy(loading = true, dismissStatus = true) }

                is Resource.Success -> _state.update {
                    it.copy(
                         restaurantVO =  result.data,
                        loading = false,
                        message = "",
                        successStatus = true,
                        dismissStatus = true
                    )
                }
            }




        }
    }


    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(dismissStatus = true, goToLogin = true)
        }
    }
}