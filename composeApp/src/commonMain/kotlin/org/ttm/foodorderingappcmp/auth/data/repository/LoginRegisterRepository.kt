package org.ttm.foodorderingappcmp.auth.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO
import org.ttm.foodorderingappcmp.auth.network.api_service.LoginRegisterService
import org.ttm.foodorderingappcmp.auth.network.impls.LoginRegisterServiceImpl
import org.ttm.foodorderingappcmp.auth.network.responses.toVO
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider

object LoginRegisterRepository {

    val loginRegisterService : LoginRegisterService = LoginRegisterServiceImpl

    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun login(email: String, password: String): Resource<LoginRegisterVO> =
        withContext(Dispatchers.IO) {
            try {
                // Call API
                val response = loginRegisterService.login(email = email, password = password)
                val vo = response?.toVO()
                    ?: return@withContext Resource.Error("Empty login response")

                launch {
                    response.toVO().let {
                        appDatabase.userDao().deleteAllUser()
                        appDatabase.userDao().insertUser(it)
                    }
                }

                Resource.Success(vo)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "Login failed. Please try again.")
            }
        }


    suspend fun register(email: String, fullName: String, password: String) : Resource<LoginRegisterVO> =
        withContext(Dispatchers.IO) {
            try {
                // Call API
                val response = loginRegisterService.register(
                    email = email,
                    fullName = fullName,
                    password = password
                )
                val vo = response?.toVO()
                    ?: return@withContext Resource.Error("Empty register response")

                launch {
                    response.toVO().let {
                        appDatabase.userDao().deleteAllUser()
                        appDatabase.userDao().insertUser(it)
                    }
                }

                Resource.Success(vo)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "Registration failed. Please try again.")
            }
        }

//    suspend fun getAllUserData():  List<LoginRegisterVO> {
//        return appDatabase.userDao().getAllUsers()
//    }

    suspend fun deleteAllUserData(){
        appDatabase.userDao().deleteAllUser()
    }
}