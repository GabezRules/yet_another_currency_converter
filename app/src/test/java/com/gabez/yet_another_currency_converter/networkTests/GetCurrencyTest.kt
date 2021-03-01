package com.gabez.yet_another_currency_converter.networkTests

import android.util.Log
import com.gabez.yet_another_currency_converter.data.apiService.NetworkClientImpl
import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class GetCurrencyTest {

    @Test
    fun getCurrencyTest(){
        val currencyCode = "PLN"
        val networkClient = NetworkClientImpl()
        var currencyFromApi: Any? = null

        GlobalScope.launch{
            currencyFromApi = networkClient.getCurrency(currencyCode, "a")
        }.invokeOnCompletion{
            assertEquals((currencyFromApi as CurrencyFromApi).Code, currencyCode)
        }
    }
}