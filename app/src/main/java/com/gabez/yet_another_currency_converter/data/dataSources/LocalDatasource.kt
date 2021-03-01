package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse

interface LocalDatasource {
    suspend fun calculate(request: CalculateRequest): CalculateResponse
}