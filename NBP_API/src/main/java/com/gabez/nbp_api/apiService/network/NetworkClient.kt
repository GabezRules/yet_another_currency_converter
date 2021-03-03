package com.gabez.nbp_api.apiService.network

import com.gabez.nbp_api.apiService.responses.ApiAllCurrenciesResponse

interface NetworkClient {
    suspend fun getAllCurrenciesMinimal(): ApiAllCurrenciesResponse
}