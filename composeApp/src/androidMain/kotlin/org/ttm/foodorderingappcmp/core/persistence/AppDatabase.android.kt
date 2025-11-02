package org.ttm.foodorderingappcmp.core.persistence

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilderAndroid(appContext: Context): RoomDatabase.Builder<AppDatabase>{
    val dbFile = appContext.getDatabasePath("food_ordering_app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}