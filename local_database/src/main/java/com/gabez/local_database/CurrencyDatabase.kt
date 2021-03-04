package com.gabez.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabez.local_database.entities.CurrencyEntity
import com.gabez.local_database.entities.RateEntity

@Database(entities = [RateEntity::class, CurrencyEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun dao(): CurrenciesDao

    companion object{
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, CurrencyDatabase::class.java, "currency-db").build()
    }
}
