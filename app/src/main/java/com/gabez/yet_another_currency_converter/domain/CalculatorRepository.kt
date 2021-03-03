package com.gabez.yet_another_currency_converter.domain

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface CalculatorRepository {
    suspend fun getAllCurrencies(hasInternetConnection: Boolean): GetCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
}