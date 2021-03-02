package com.gabez.yet_another_currency_converter.data.localDb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val currencyName: String = "",
    val code: String = "",
    val isFavourite: Boolean = false,
    val mid: Float)