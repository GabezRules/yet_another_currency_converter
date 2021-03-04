package com.gabez.yet_another_currency_converter.chart.data.datasources

import com.gabez.data_access.common.GetRatesResponse

interface RemoteDatasourceChart {
    suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): GetRatesResponse
}