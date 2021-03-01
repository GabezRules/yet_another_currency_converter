package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.yet_another_currency_converter.data.apiService.NetworkClient
import com.gabez.yet_another_currency_converter.data.apiService.NetworkClientProvider
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse

class RemoteDatasourceImpl: RemoteDatasource {
    private val service: NetworkClient = NetworkClientProvider.provideNetworkClient()
    override suspend fun calculate(request: CalculateRequest): CalculateResponse = service.calculate(request)
}