package com.gabez.yet_another_currency_converter.data.apiService.network

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse

interface NetworkClient {
    suspend fun calculate(request: CalculateRequest): CalculateResponse
    suspend fun getAllCurrencies(): GetAllCurrenciesResponse
}