package com.gabez.data_access.apiFacade

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.GetRatesResponse

interface ApiFacade {
    suspend fun getCurrencies(): GetCurrenciesResponse
    suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): GetRatesResponse
}