package com.gabez.yet_another_currency_converter.calculator.domain.calculations

data class CalculateResponse(val flag: CalculateResponseStatus, val amount: Float?, val error: Any?)