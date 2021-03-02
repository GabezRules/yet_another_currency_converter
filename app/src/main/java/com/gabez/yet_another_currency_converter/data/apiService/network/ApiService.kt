package com.gabez.yet_another_currency_converter.data.apiService.network

import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyTableFromAPI
import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyFromAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("rates/{table}/{code}/?format=json")
    fun getCurrencyFromTable(@Path("code") code: String, @Path("table") table: String): Call<CurrencyFromAPI>

    @GET("tables/{table}/?format=json")
    fun getAllFromTable(@Path("table") table: String): Call<List<CurrencyTableFromAPI>>
}