package com.gabez.yet_another_currency_converter.data.localDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabez.yet_another_currency_converter.data.localDb.entities.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun dao(): CurrenciesDao

    companion object{
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, CurrencyDatabase::class.java, "currency-db").build()
    }
}
