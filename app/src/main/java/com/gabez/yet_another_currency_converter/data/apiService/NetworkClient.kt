package com.gabez.yet_another_currency_converter.data.apiService

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.domain.response.GetAllCurrenciesResponse

interface NetworkClient {
    suspend fun calculate(request: CalculateRequest): CalculateResponse
    suspend fun getAllCurrencies(): GetAllCurrenciesResponse
}