package com.gabez.yet_another_currency_converter.chart.data.datasources

import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.data_access.localDbFacade.LocalDbFacade

class LocalDatasourceChartImpl(private val facade: LocalDbFacade): LocalDatasourceChart {
    override suspend fun getFavouriteCurrencies(): List<CurrencyUniversal> = facade.getFavCurrencies()
}