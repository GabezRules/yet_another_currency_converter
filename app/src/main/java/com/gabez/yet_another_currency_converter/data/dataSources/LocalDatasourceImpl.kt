package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponseStatus

class LocalDatasourceImpl: LocalDatasource {
    override suspend fun calculate(request: CalculateRequest): CalculateResponse {

        //TODO: Implement
        return CalculateResponse(
            flag = CalculateResponseStatus.NOT_VALID,
            data = ""
        )
    }
}