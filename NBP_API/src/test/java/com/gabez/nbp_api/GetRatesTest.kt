package com.gabez.nbp_api

import com.gabez.nbp_api.apiService.network.NetworkClientImpl
import com.gabez.nbp_api.apiService.responses.ApiResponseStatus
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetRatesTest {
    @Test
    fun getRatesTest() {
        runBlocking {
            val client = NetworkClientImpl()
            val rates = client.getRates("usd", "dolar amerykański", "2020-01-01", "2020-02-02")
            assertEquals(rates.error, null)
        }
    }
}