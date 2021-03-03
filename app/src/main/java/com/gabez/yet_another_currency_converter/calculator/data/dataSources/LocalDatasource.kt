package com.gabez.yet_another_currency_converter.calculator.data.dataSources

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

interface LocalDatasource {
    suspend fun getCurrencies(): GetCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
    suspend fun getFavourites(): List<CurrencyUniversal>
}