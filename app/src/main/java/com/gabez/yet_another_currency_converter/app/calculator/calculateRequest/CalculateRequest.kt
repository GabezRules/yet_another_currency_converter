package com.gabez.yet_another_currency_converter.app.calculator.calculateRequest

import com.gabez.yet_another_currency_converter.entities.CurrencyForView

data class CalculateRequest(
    val firstCurrency: CurrencyForView?,
    val secondCurrency: CurrencyForView?,
    val amount: Float?
)