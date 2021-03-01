package com.gabez.yet_another_currency_converter.data.apiService

import android.content.Context
import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import com.gabez.yet_another_currency_converter.domain.calculations.CalculationsHelper
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponseData
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponseStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClientImpl: NetworkClient {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }

    @ExperimentalCoroutinesApi
    override suspend fun calculate(request: CalculateRequest): CalculateResponse {
        val firstCurrency = getCurrency(request.firstCurrencyShortName)
        val secondCurrency = getCurrency(request.secondCurrencyShortName)

        var responseData: Any? = null

        responseData = if(firstCurrency != null && secondCurrency != null){
            CalculateResponseData(
                amount = CalculationsHelper.getResult(request.amount, firstCurrency.Mid, secondCurrency.Mid)
            )
        } else "Unknown error"

        return CalculateResponse(
            flag = CalculateResponseStatus.SUCCESS,
            data = responseData
        )

    }

    @ExperimentalCoroutinesApi
    suspend fun getCurrency(code: String): CurrencyFromApi? {
        var currencyFromA = service.getCurrencyFromA(code).awaitResponse()
        var currencyFromB = service.getCurrencyFromB(code).awaitResponse()

        return when {
            currencyFromA.isSuccessful && currencyFromA.code() != 404 -> currencyFromA.body()!!
            currencyFromB.isSuccessful && currencyFromB.code() != 404 -> currencyFromB.body()!!
            else -> null
        }
    }
}