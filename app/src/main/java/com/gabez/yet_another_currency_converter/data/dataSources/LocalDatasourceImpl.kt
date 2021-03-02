package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.data.localDb.CurrencyDatabase
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class LocalDatasourceImpl(private val localDb: CurrencyDatabase ): LocalDatasource {

    override suspend fun calculate(request: CalculateRequest): CalculateResponse {

        //TODO: Implement
        return CalculateResponse(
            flag = ResponseStatus.NOT_VALID,
            amount = 0f,
            error = null
        )
    }

    override suspend fun getCurrencies(): GetAllCurrenciesResponse {
        return GetAllCurrenciesResponse(
            flag = ResponseStatus.SUCCESS,
            data = localDb.dao().getAllCurrencies().map { currencyEntity ->
                CurrencyForView(
                    code = currencyEntity.code,
                    currencyName = currencyEntity.currencyName,
                    mid = currencyEntity.mid,
                    isFavourite = currencyEntity.isFavourite
            ) }
        )
    }
}