package org.ttm.foodorderingappcmp.app.data

import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider

object AppRepository {
    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getAllUserData():  List<LoginRegisterVO> {
        return appDatabase.userDao().getAllUsers()
    }

}