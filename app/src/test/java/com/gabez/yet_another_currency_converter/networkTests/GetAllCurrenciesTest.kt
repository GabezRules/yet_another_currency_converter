package com.gabez.yet_another_currency_converter.networkTests

import android.util.Log
import com.gabez.yet_another_currency_converter.data.apiService.NetworkClientImpl
import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import com.gabez.yet_another_currency_converter.domain.response.GetAllCurrenciesResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class GetAllCurrenciesTest {

    @Test
    fun getAllCurrenciesTest(){
        val networkClient = NetworkClientImpl()
        var response: GetAllCurrenciesResponse? = null

        GlobalScope.launch{
            response = networkClient.getAllCurrencies()
        }.invokeOnCompletion{
            Log.v("CURRENCY_TEST" ,response!!.data.toString())

            assertEquals(response != null, true)
            assertEquals((response!!.data as List<CurrencyFromApi>).size > 0, true)
        }
    }
}