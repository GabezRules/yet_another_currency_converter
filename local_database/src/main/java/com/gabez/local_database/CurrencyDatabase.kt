package com.gabez.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun dao(): CurrenciesDao

    companion object{
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, CurrencyDatabase::class.java, "currency-db").build()
    }
}
