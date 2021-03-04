package com.gabez.nbp_api.apiService.network

import com.gabez.nbp_api.apiService.BASE_URL
import com.gabez.nbp_api.apiService.entities.CurrencyFromAPI
import com.gabez.nbp_api.apiService.entities.SingleRateFromAPI
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
                            SingleRateFromAPI("", "", item.mid)
                        )
                    )
                )
            }

            currenciesFromB.body()!![0].rates.map { item ->
                allCurrencies.add(
                    CurrencyFromAPI(
                        item.code, item.currency, listOf(
                            SingleRateFromAPI("", "", item.mid)
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
            error = currenciesFromA.raw().message()+" "+currenciesFromA.raw().code()
        )
    }

    override suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): ApiGetCurrencyResponse {
        val ratesFromA = service.getRates("a", code.toLowerCase(), dateFrom, dateTo).awaitResponse()
        val ratesFromB = service.getRates("b", code, dateFrom.toLowerCase(), dateTo).awaitResponse()

        var data: CurrencyFromAPI? = null
        var error: String? = null
        var status: ApiResponseStatus = ApiResponseStatus.FAILED

        when {
            ratesFromA.isSuccessful -> {
                ratesFromA.body()?.let {
                    if (it.code == code && it.currency == currencyName) {
                        data = it
                        status = ApiResponseStatus.SUCCESS
                    }
                }
            }
            ratesFromB.isSuccessful -> {
                ratesFromB.body()?.let {
                    if (it.code == code && it.currency == currencyName) {
                        data = it
                        status = ApiResponseStatus.SUCCESS
                    }
                }
            }
            else -> error = ratesFromA.raw().message()+" "+ratesFromA.raw().code()
        }

        return ApiGetCurrencyResponse(
            status = status,
            data = data,
            error = error
        )
    }
}