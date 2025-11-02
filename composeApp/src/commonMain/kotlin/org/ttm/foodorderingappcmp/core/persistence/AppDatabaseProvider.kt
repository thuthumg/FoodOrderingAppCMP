package org.ttm.foodorderingappcmp.core.persistence

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


object AppDatabaseProvider {
    //Variable
    lateinit var appDatabase: AppDatabase

    
    //fun
    fun initializeDatabase(databaseBuilder: RoomDatabase.Builder<AppDatabase>){
        appDatabase = databaseBuilder
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}