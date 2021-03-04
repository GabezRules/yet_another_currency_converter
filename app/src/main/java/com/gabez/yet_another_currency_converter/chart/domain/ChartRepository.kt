package com.gabez.yet_another_currency_converter.chart.domain

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.entities.CurrencyUniversal

interface ChartRepository {
    suspend fun getFavouriteCurrencies(): List<CurrencyUniversal>
}