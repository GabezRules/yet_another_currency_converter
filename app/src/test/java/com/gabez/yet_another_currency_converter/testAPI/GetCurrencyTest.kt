package com.gabez.yet_another_currency_converter.testAPI

import com.gabez.nbp_api.apiService.network.NetworkClientImpl
import com.gabez.data_access.CurrencyFromAPI
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import org.junit.Test

class GetCurrencyTest {

    @ExperimentalCoroutinesApi
    @Test fun getCurrencyTest() {
        runBlocking {
            val currencyFromApi = NetworkClientImpl().getCurrency("USD", "a")
            assertEquals((currencyFromApi.data as CurrencyFromAPI).rates[0].mid.toString(), "3.7572")
        }
    }
}