package com.gabez.yet_another_currency_converter.data.apiService.network

import com.gabez.yet_another_currency_converter.data.apiService.BASE_URL
import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyFromAPI
import com.gabez.yet_another_currency_converter.domain.calculations.CalculationsHelper
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetCurrencyResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.data.apiService.entities.RateInCurrencyFromAPI
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
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

        return if (firstCurrency.status == ResponseStatus.SUCCESS && secondCurrency.status == ResponseStatus.SUCCESS) {
            val result = CalculationsHelper.getResult(
                request.amount,
                firstCurrency.data!!.rates[0].mid,
                secondCurrency.data!!.rates[0].mid
            )

            CalculateResponse(
                flag = ResponseStatus.SUCCESS,
                amount = result,
                error = null
            )
        } else CalculateResponse(
            flag = ResponseStatus.FAILED,
            amount = null,
            error = "error"
        )

    }

    override suspend fun getAllCurrencies(): GetAllCurrenciesResponse {
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

            GetAllCurrenciesResponse(
                flag = ResponseStatus.SUCCESS,
                data = allCurrencies.map { item -> item.toCurrencyForView() }
            )
        } else GetAllCurrenciesResponse(
            flag = ResponseStatus.FAILED,
            error = currenciesFromA.errorBody().toString() + " " + currenciesFromB.errorBody()
                .toString()
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun getCurrency(code: String, longName: String): GetCurrencyResponse {
        val currencyFromA =
            service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "a").awaitResponse()
        val currencyFromB =
            service.getCurrencyFromTable(code.toLowerCase(Locale.ROOT), "b").awaitResponse()

        return when {
            currencyFromA.isSuccessful && !currencyFromA.errorBody().toString()
                .contains("404") -> GetCurrencyResponse(
                ResponseStatus.SUCCESS,
                data = currencyFromA.body()!!
            )
            currencyFromB.isSuccessful && !currencyFromB.errorBody().toString()
                .contains("404") -> GetCurrencyResponse(
                ResponseStatus.SUCCESS,
                data = currencyFromB.body()!!
            )
            currencyFromA.code().toString().contains("404") && currencyFromB.code().toString()
                .contains("404") -> GetCurrencyResponse(
                ResponseStatus.FAILED,
                error = currencyFromA.raw().code().toString() + " " + currencyFromA.message()
            )
            else -> GetCurrencyResponse(
                ResponseStatus.FAILED,
                error = currencyFromA.raw().code().toString() + " " + currencyFromA.message()
            )
        }
    }

    fun CurrencyFromAPI.toCurrencyForView(): CurrencyForView {
        return CurrencyForView(
            code = this.currency,
            currencyName = this.code
        )
    }
}