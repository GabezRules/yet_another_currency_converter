package com.gabez.data_access.apiFacade

import com.gabez.data_access.common.GetCurrenciesResponse

interface ApiFacade {
    suspend fun getCurrencies(): GetCurrenciesResponse
}