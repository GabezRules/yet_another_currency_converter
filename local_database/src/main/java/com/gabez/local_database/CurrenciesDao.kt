package com.gabez.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gabez.local_database.entities.CurrencyEntity
import com.gabez.local_database.entities.RateEntity

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
    fun insertAllCurrencies(users: List<CurrencyEntity>)

    @Query("DELETE FROM currency")
    fun deleteAllCurrencies()

    @Transaction
    fun updateCurrencies(users: List<CurrencyEntity>) {
        deleteAllCurrencies()
        insertAllCurrencies(users)
    }

    @Query("SELECT * FROM rate WHERE parentCurrencyName = :parentName AND parentCode = :code AND NOT (effectiveDate > :dateFrom OR effectiveDate < :dateTo)")
    fun getRatesFromCurrency(parentName: String, code: String, dateFrom: String, dateTo: String): List<RateEntity>

    @Insert
    fun insertAllRates(rates: List<RateEntity>)

    @Query("DELETE FROM rate")
    fun deleteAllRates()

    @Transaction
    fun updateRates(rates: List<RateEntity>) {
        deleteAllRates()
        insertAllRates(rates)
    }
}