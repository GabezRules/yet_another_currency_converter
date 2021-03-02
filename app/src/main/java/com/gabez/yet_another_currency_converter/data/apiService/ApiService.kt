package com.gabez.yet_another_currency_converter.data.apiService

import com.gabez.yet_another_currency_converter.data.apiService.responses.CurrencyTableFromApi
import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("rates/{table}/{code}/?format=json")
    fun getCurrencyFromTable(@Path("code") code: String, @Path("table") table: String): Call<CurrencyFromApi>

    @GET("tables/{table}/?format=json")
    fun getAllFromTable(@Path("table") table: String): Call<List<CurrencyTableFromApi>>
}