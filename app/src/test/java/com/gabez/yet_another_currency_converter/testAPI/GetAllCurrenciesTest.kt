package com.gabez.yet_another_currency_converter.testAPI

import com.gabez.nbp_api.apiService.network.NetworkClientImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetAllCurrenciesTest {

    @Test fun getAllCurrencies() {
        runBlocking {
            val response = NetworkClientImpl().getAllCurrenciesMinimal().data!![0]
            assertEquals(response.mid.toString(), "this shouldn't work")
        }
    }
}