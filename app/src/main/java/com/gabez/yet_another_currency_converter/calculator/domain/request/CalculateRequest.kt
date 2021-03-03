package com.gabez.yet_another_currency_converter.calculator.domain.request

data class CalculateRequest(
    val firstCurrencyShortName: String = "",
    val secondCurrencyShortName: String = "",
    val amount: Float = 0f
)