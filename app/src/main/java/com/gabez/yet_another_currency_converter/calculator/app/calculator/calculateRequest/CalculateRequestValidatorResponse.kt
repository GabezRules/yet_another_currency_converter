package com.gabez.yet_another_currency_converter.calculator.app.calculator.calculateRequest

data class CalculateRequestValidatorResponse(val isFirstCurrencyValid: Boolean = false, val isSecondCurrencyValid: Boolean = false, val isAmountValid: Boolean = false, var isValid: Boolean = false)