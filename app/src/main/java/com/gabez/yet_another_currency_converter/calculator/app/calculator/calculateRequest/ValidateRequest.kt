package com.gabez.yet_another_currency_converter.calculator.app.calculator.calculateRequest

import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

data class ValidateRequest(
    val firstCurrency: CurrencyForView?,
    val secondCurrency: CurrencyForView?,
    val amount: Float?
)