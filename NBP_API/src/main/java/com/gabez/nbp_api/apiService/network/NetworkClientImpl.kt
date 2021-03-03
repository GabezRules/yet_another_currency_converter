package com.gabez.nbp_api.apiService.network

import com.gabez.nbp_api.apiService.BASE_URL
import com.gabez.nbp_api.apiService.entities.CurrencyFromAPI
import com.gabez.nbp_api.apiService.entities.RateInCurrencyFromAPI
import com.gabez.nbp_api.apiService.responses.*
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClientImpl : NetworkClient {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }

    override suspend fun getAllCurrenciesMinimal(): ApiAllCurrenciesResponse {
        val currenciesFromA = service.getAllFromTable("a").awaitResponse()
        val currenciesFromB = service.getAllFromTable("b").awaitResponse()
        val allCurrencies = arrayListOf<CurrencyFromAPI>()

        return if (currenciesFromA.isSuccessful && currenciesFromB.isSuccessful && currenciesFromA.body()
                ?.isNullOrEmpty() == false && currenciesFromB.body()?.isNullOrEmpty() == false
        ) {
            currenciesFromA.body()!![0].rates.map { item ->
                allCurrencies.add(
                    CurrencyFromAPI(
                        item.code, item.currency, listOf(
                            RateInCurrencyFromAPI("", "", item.mid)
                        )
                    )
                )
            }

            currenciesFromB.body()!![0].rates.map { item ->
                allCurrencies.add(
                    CurrencyFromAPI(
                        item.code, item.currency, listOf(
                            RateInCurrencyFromAPI("", "", item.mid)
                        )
                    )
                )
            }

            ApiAllCurrenciesResponse(
                flag = ApiResponseStatus.SUCCESS,
                data = allCurrencies
            )
        } else ApiAllCurrenciesResponse(
            flag = ApiResponseStatus.FAILED,
            error = currenciesFromA.errorBody().toString() + " " + currenciesFromB.errorBody()
                .toString()
        )
    }
}