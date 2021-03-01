package com.gabez.yet_another_currency_converter.data

import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.RemoteDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.providers.LocalDatasourceProvider
import com.gabez.yet_another_currency_converter.data.dataSources.providers.RemoteDatasourceProvider
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionHelper

class CalculatorRepositoryImpl : CalculatorRepository {
    private val internetConnectionHelper = InternetConnectionHelper()
    private val remoteDatasource: RemoteDatasource = RemoteDatasourceProvider.provideRemoteDatasource()
    private val localDatasource: LocalDatasource = LocalDatasourceProvider.provideLocalDatasource()

    override suspend fun calculate(request: CalculateRequest): CalculateResponse {
        return if (internetConnectionHelper.hasInternetConnection()) remoteDatasource.calculate(request)
        else localDatasource.calculate(request)
    }
}