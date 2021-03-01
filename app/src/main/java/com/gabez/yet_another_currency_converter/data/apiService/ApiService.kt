package com.gabez.yet_another_currency_converter.data.apiService

import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("A/{code}")
    fun getCurrencyFromA(code: String): Call<CurrencyFromApi>

    @GET("B/{code}")
    fun getCurrencyFromB(code: String): Call<CurrencyFromApi>
}