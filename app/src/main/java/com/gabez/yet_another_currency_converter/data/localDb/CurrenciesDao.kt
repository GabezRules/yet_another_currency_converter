package com.gabez.yet_another_currency_converter.data.localDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gabez.yet_another_currency_converter.data.localDb.entities.CurrencyEntity

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM currency")
    fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE currencyName = :currency AND code = :code")
    fun getCurrency(currency: String, code: String): CurrencyEntity

    @Query("UPDATE currency SET isFavourite = :isFavourite WHERE currencyName = :currencyName")
    suspend fun setFavourite(currencyName: String, isFavourite: Boolean)

    @Query("SELECT * FROM currency WHERE isFavourite")
    fun getAllFavourites(): List<CurrencyEntity>

    @Insert
    fun insertAll(users: List<CurrencyEntity>)

    @Query("DELETE FROM currency")
    fun deleteAll()

    @Transaction
    fun updateData(users: List<CurrencyEntity>) {
        deleteAll()
        insertAll(users)
    }
}