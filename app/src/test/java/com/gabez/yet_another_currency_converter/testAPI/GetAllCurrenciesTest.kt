package com.gabez.yet_another_currency_converter.testAPI

import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClientImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetAllCurrenciesTest {

    @Test fun getAllCurrencies() {
        runBlocking {
            val response = NetworkClientImpl().getAllCurrencies()
            assertEquals(response.data.toString(), "this shouldn't work")
        }
    }
}