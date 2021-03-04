package com.gabez.yet_another_currency_converter.chart.app.chartValidator

data class ChartDataRequestValidatorResponse(val isFirstDateValid: Boolean, val isSecondDateValid: Boolean, val isCurrencyValid: Boolean, var isChronologyValid: Boolean = false, var isValid: Boolean = false)