package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface LocalDatasource {
    suspend fun getCurrencies(): GetAllCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
}