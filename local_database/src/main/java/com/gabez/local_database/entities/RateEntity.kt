package com.gabez.local_database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rate")
data class RateEntity(
    val parentCurrencyName: String = "",
    val parentCode: String = "",
    @PrimaryKey
    val no: String = "",
    val effectiveDate: Date = Date(),
    val mid: Float = 0f
)