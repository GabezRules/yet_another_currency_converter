package com.gabez.yet_another_currency_converter.networkTests

import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClientImpl
import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyFromAPI
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