package com.gabez.data_access.apiFacade

import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.ResponseStatus
import com.gabez.nbp_api.apiService.entities.CurrencyFromAPI
import com.gabez.nbp_api.apiService.network.NetworkClient
import com.gabez.nbp_api.apiService.responses.ApiAllCurrenciesResponse
import com.gabez.nbp_api.apiService.responses.ApiResponseStatus


//TODO: Use internet connection monitor in data_access
class ApiFacadeImpl(private val networkClient: NetworkClient): ApiFacade {
    override suspend fun getCurrencies(): GetCurrenciesResponse {
        return networkClient.getAllCurrenciesMinimal().toUniversalResponse()
    }

    private fun ApiAllCurrenciesResponse.toUniversalResponse(): GetCurrenciesResponse {
        return GetCurrenciesResponse(
            flag = when(this.flag){
                ApiResponseStatus.FAILED -> ResponseStatus.FAILED
                ApiResponseStatus.SUCCESS -> ResponseStatus.SUCCESS
            },
            data = this.data.toUniversalCurrencies(),
            error = this.error
        )
    }

    private fun List<CurrencyFromAPI>?.toUniversalCurrencies(): List<CurrencyUniversal>?{
        this?.let { list ->
            return list.map {it.toUniversalCurrency()}
        }?: return null
    }

    private fun CurrencyFromAPI.toUniversalCurrency(): CurrencyUniversal {
        return CurrencyUniversal(
            currencyName = this.currency,
            code = this.code,
            no = this.rates[0].no,
            date = this.rates[0].effectiveDate,
            mid = this.rates[0].mid
        )
    }

}