package com.gabez.yet_another_currency_converter.data.localDb

import androidx.room.*
import com.gabez.yet_another_currency_converter.data.localDb.entities.CurrencyEntity
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

@Dao
interface CurrenciesDao {
    @Query("SELECT * FROM currency")
    fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE currencyName LIKE :currency AND code LIKE :code")
    fun getCurrency(currency: String, code: String): CurrencyEntity

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