package com.gabez.yet_another_currency_converter.domain

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface CalculatorRepository {
    suspend fun getAllCurrencies(hasInternetConnection: Boolean): GetAllCurrenciesResponse
    suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView)
}