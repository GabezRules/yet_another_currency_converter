package com.gabez.yet_another_currency_converter.data.apiService

import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import com.gabez.yet_another_currency_converter.domain.calculations.CalculationsHelper
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponseData
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetCurrencyResponse
import com.gabez.yet_another_currency_converter.domain.response.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.data.entities.RateFromApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

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

    @ExperimentalCoroutinesApi
    override suspend fun calculate(request: CalculateRequest): CalculateResponse {
        val firstCurrency =
            getCurrency(request.firstCurrencyShortName, request.firstCurrencyShortName)
        val secondCurrency =
            getCurrency(request.secondCurrencyShortName, request.secondCurrencyShortName)

        return if(firstCurrency.status == ResponseStatus.SUCCESS && secondCurrency.status == ResponseStatus.SUCCESS){
            val responseData = CalculateResponseData(
                amount = CalculationsHelper.getResult(
                    request.amount,
                    (firstCurrency.data as CurrencyFromApi).rates[0].mid,
                    (secondCurrency.data as CurrencyFromApi).rates[0].mid
                )
            )

            CalculateResponse(
                flag = ResponseStatus.SUCCESS,
                data = responseData
            )
        } else CalculateResponse(
            flag = ResponseStatus.FAILED,
            data = "error"
        )

    }

    override suspend fun getAllCurrencies(): GetAllCurrenciesResponse {
        val currenciesFromA = service.getAllFromTable("a").awaitResponse()
        val currenciesFromB = service.getAllFromTable("b").awaitResponse()
        val allCurrencies = arrayListOf<CurrencyFromApi>()

        return if (currenciesFromA.isSuccessful && currenciesFromB.isSuccessful) {
            currenciesFromA.body()!![0].rates.map { item -> allCurrencies.add(CurrencyFromApi(item.code, item.currency, listOf(
                RateFromApi("","",item.mid)
            )))}
            currenciesFromB.body()!![0].rates.map { item -> allCurrencies.add(CurrencyFromApi(item.code, item.currency, listOf(
                RateFromApi("","",item.mid)
            )))}

            GetAllCurrenciesResponse(
                flag = ResponseStatus.SUCCESS,
                data = allCurrencies
            )
        }else GetAllCurrenciesResponse(
            flag = ResponseStatus.FAILED,
            data = currenciesFromA.errorBody().toString() + " " + currenciesFromB.errorBody().toString()
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun getCurrency(code: String, longName: String): GetCurrencyResponse {
        val currencyFromA =
            service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "a").awaitResponse()
        val currencyFromB =
            service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "b").awaitResponse()

        return when {
            currencyFromA.isSuccessful && !currencyFromA.errorBody().toString().contains("404") -> GetCurrencyResponse(ResponseStatus.SUCCESS, currencyFromA.body()!!)
            currencyFromB.isSuccessful && !currencyFromB.errorBody().toString().contains("404") -> GetCurrencyResponse(ResponseStatus.SUCCESS, currencyFromB.body()!!)
            currencyFromA.code().toString().contains("404") && currencyFromB.code().toString().contains("404") -> GetCurrencyResponse(ResponseStatus.FAILED,currencyFromA.raw().code().toString()+" "+ currencyFromA.message())
            else -> GetCurrencyResponse(ResponseStatus.FAILED, currencyFromA.raw().code().toString()+" "+currencyFromA.message())
        }
    }
}