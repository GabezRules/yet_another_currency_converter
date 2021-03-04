package com.gabez.yet_another_currency_converter.chart.data.datasources

import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.common.GetRatesResponse

class RemoteDatasourceChartImpl(private val facade: ApiFacade): RemoteDatasourceChart {
    override suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): GetRatesResponse = facade.getRates(code, currencyName, dateFrom, dateTo)
}