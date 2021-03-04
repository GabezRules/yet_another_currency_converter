package com.gabez.yet_another_currency_converter.chart.domain.usecases

import com.gabez.data_access.common.GetRatesResponse
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.chart.domain.ChartRepository

class GetChartDataUsecase(private val repo: ChartRepository) {
    var dateTo: String = ""
    var dateFrom: String = ""
    var currency: CurrencyForView? = null

    //suspend fun invoke(): GetRatesResponse = repo.getRates(currency!!.code, currency!!.currencyName, dateFrom, dateTo)
    suspend fun invoke(): GetRatesResponse = repo.getRates("usd", "dolar amerykański", "2020-01-01", "2020-02-02")
}