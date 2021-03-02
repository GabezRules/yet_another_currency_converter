package com.gabez.yet_another_currency_converter.entities

class CurrencyForView(
    val code: String = "PLN",
    val currencyName: String = "Złoty polski",
    val isFavourite: Boolean = false,
    var mid: Float = 0f
)