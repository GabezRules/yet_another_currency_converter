package com.gabez.nbp_api.apiService.network

import com.gabez.nbp_api.apiService.responses.ApiAllCurrenciesResponse
import com.gabez.nbp_api.apiService.responses.ApiGetCurrencyResponse

interface NetworkClient {
    suspend fun getAllCurrenciesMinimal(): ApiAllCurrenciesResponse
    suspend fun getRates(code: String, currencyName: String, dateFrom: String, dateTo: String): ApiGetCurrencyResponse
}