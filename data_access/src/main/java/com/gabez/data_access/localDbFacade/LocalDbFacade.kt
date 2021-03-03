package com.gabez.data_access.localDbFacade

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.entities.CurrencyUniversal

interface LocalDbFacade {
   suspend fun getCurrencies(): GetCurrenciesResponse
   suspend fun markCurrency(currency: String, isFavourite: Boolean)
   suspend fun getFavCurrencies(): List<CurrencyUniversal>
}