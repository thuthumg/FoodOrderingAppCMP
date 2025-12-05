package org.ttm.foodorderingappcmp.core.network

sealed interface FoodOrderingResult<out D, out E> {
    data class Success<out D>(val data: D) : FoodOrderingResult<D, Nothing>
    data class Failure<out E>(val error: E) : FoodOrderingResult<Nothing,E>
}

inline fun< D, E> FoodOrderingResult< D, E>.onSuccess(action: (D) -> Unit): FoodOrderingResult<D,E>{
    return when(this){
        is FoodOrderingResult.Failure -> this
        is FoodOrderingResult.Success -> {
            action(this.data)
            this
        }
    }
}

inline fun< D, E> FoodOrderingResult< D, E>.onError(errorAction: (E) -> Unit): FoodOrderingResult<D,E>{
    return when(this){
        is FoodOrderingResult.Failure -> {
            errorAction(this.error)
            this
        }
        is FoodOrderingResult.Success -> this
    }
}