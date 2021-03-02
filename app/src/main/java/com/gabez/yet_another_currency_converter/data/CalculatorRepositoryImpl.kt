package com.gabez.yet_another_currency_converter.data

import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.RemoteDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.providers.RemoteDatasourceProvider
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionHelper

class CalculatorRepositoryImpl(private val localDatasource: LocalDatasource) : CalculatorRepository {
    private val remoteDatasource: RemoteDatasource = RemoteDatasourceProvider.provideRemoteDatasource()

    override suspend fun getAllCurrencies(hasInternetConnection: Boolean): GetAllCurrenciesResponse {
        return if (hasInternetConnection) remoteDatasource.getCurrencies()
        else localDatasource.getCurrencies()
    }

    override suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView) {
        localDatasource.markCurrency(isFavourite, currency)
    }
}