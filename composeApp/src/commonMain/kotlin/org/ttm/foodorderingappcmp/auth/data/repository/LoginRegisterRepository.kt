package org.ttm.foodorderingappcmp.auth.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO
import org.ttm.foodorderingappcmp.auth.network.api_service.LoginRegisterService
import org.ttm.foodorderingappcmp.auth.network.impls.LoginRegisterServiceImpl
import org.ttm.foodorderingappcmp.auth.network.responses.toVO
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider

object LoginRegisterRepository {

    val loginRegisterService: LoginRegisterService = LoginRegisterServiceImpl

    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun login(
        email: String,
        password: String,
        onSuccess: (LoginRegisterVO) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit,
    ) = withContext(Dispatchers.IO) {

        loginRegisterService.login(email = email, password = password)
            .onSuccess { dto ->
                val vo = dto.toVO()

                // callback
                onSuccess(vo)

                // DB operations on IO
                appDatabase.userDao().deleteAllUser()
                appDatabase.userDao().insertUser(vo)
            }
            .onError { error ->
                onFailure(error.error, error.errorType)
            }
    }


    suspend fun register(
        email: String,
        fullName: String,
        password: String,
        onSuccess: (LoginRegisterVO) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit,
    ) =
        withContext(Dispatchers.IO) {

            loginRegisterService.register(
                email = email,
                fullName = fullName,
                password = password
            )
                .onSuccess {

                    onSuccess(it.toVO())

                    appDatabase.userDao().deleteAllUser()
                    appDatabase.userDao().insertUser(it.toVO())

                }.onError { error ->
                    onFailure(error.error, error.errorType)
                }


        }

    suspend fun deleteAllUserData() {
        appDatabase.userDao().deleteAllUser()
    }
}