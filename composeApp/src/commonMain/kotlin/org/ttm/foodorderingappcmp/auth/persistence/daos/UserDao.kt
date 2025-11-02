package org.ttm.foodorderingappcmp.auth.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(loginRegisterVO: LoginRegisterVO)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<LoginRegisterVO>


    @Query("DELETE FROM users")
    suspend fun deleteAllUser()
}

