package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.data.apiService.NetworkClient
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse

class RemoteDatasourceImpl(private val service: NetworkClient): RemoteDatasource {
    override suspend fun calculate(request: CalculateRequest): CalculateResponse = service.calculate(request)
}