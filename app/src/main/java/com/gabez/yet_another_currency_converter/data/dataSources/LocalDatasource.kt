package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface LocalDatasource {
    suspend fun getCurrencies(): GetCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
    suspend fun getFavourites(): List<CurrencyUniversal>
}