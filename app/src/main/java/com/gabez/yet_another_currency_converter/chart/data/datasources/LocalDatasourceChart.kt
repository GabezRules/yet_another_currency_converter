package com.gabez.yet_another_currency_converter.chart.data.datasources

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.GetRatesResponse
import com.gabez.data_access.entities.CurrencyUniversal

interface LocalDatasourceChart {
    suspend fun getFavouriteCurrencies(): List<CurrencyUniversal>
    suspend fun getRates(code: String, currencyName: String, dateFrom: String, dateTo: String): GetRatesResponse
}