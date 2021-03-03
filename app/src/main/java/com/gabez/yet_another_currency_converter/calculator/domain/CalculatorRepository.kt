package com.gabez.yet_another_currency_converter.calculator.domain

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

interface CalculatorRepository {
    suspend fun getAllCurrencies(hasInternetConnection: Boolean): GetCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
}