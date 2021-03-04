package com.gabez.yet_another_currency_converter.chart.data.datasources

import com.gabez.data_access.common.GetRatesResponse
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.data_access.localDbFacade.LocalDbFacade

class LocalDatasourceChartImpl(private val facade: LocalDbFacade) : LocalDatasourceChart {
    override suspend fun getFavouriteCurrencies(): List<CurrencyUniversal> =
        facade.getFavCurrencies()

    override suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): GetRatesResponse = facade.getRates(code, currencyName, dateFrom, dateTo)
}