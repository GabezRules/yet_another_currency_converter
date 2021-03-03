package com.gabez.yet_another_currency_converter.chart.domain

import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

class GetChartDataUsecase {
    var dateTo: String = ""
    var dateFrom: String = ""
    var currency: CurrencyForView? = null
}