package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.domain.response.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus

class LocalDatasourceImpl: LocalDatasource {
    override suspend fun calculate(request: CalculateRequest): CalculateResponse {

        //TODO: Implement
        return CalculateResponse(
            flag = ResponseStatus.NOT_VALID,
            data = ""
        )
    }

    override suspend fun getCurrencies(): GetAllCurrenciesResponse {

        //TODO: Implement
        return GetAllCurrenciesResponse(
            flag = ResponseStatus.NOT_VALID,
            data = ""
        )
    }
}