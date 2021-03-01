package com.gabez.yet_another_currency_converter.data.apiService

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
import java.util.*

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
        val firstCurrency = getCurrency(request.firstCurrencyShortName, request.firstCurrencyShortName)
        val secondCurrency = getCurrency(request.secondCurrencyShortName, request.secondCurrencyShortName)

        return if(firstCurrency != null && secondCurrency != null){
            val responseData = CalculateResponseData(
                amount = CalculationsHelper.getResult(request.amount, firstCurrency.Mid, secondCurrency.Mid)
            )

            CalculateResponse(
                flag = CalculateResponseStatus.SUCCESS,
                data = responseData
            )
        } else CalculateResponse(
            flag = CalculateResponseStatus.FAILED,
            data = "error"
        )

    }

    @ExperimentalCoroutinesApi
    suspend fun getCurrency(code: String, longName: String): CurrencyFromApi? {
        val currencyFromA = service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "a").awaitResponse()
        val currencyFromB = service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "b").awaitResponse()

        return when {
            currencyFromA.isSuccessful && currencyFromA.code() != 404 -> currencyFromA.body()!!
            currencyFromB.isSuccessful && currencyFromB.code() != 404 -> currencyFromB.body()!!
            else -> null
        }
    }
}