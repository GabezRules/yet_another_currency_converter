package com.gabez.local_database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromDateToString(date: Date): String{
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    @TypeConverter
    fun fromStringToDate(string: String): Date = SimpleDateFormat("yyyy-MM-dd").parse(string)
}
