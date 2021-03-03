package com.gabez.local_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val currencyName: String = "",
    val code: String = "",
    val isFavourite: Boolean = false,
    val mid: Float)