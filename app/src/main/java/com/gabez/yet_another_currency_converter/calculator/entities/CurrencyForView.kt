package com.gabez.yet_another_currency_converter.calculator.entities

class CurrencyForView(
    val code: String = "PLN",
    val currencyName: String = "Złoty polski",
    var isFavourite: Boolean = false,
    var mid: Float = 0f
)