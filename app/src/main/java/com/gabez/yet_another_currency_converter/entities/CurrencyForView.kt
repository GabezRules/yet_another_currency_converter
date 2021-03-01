package com.gabez.yet_another_currency_converter.entities

class CurrencyForView(
    val nameShort: String = "PLN",
    val nameLong: String = "Złoty polski",
    val isFavourite: Boolean = false,
    var mid: Float = 0f
)