package com.gabez.yet_another_currency_converter.chart.data

import com.gabez.data_access.common.GetRatesResponse
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.chart.data.datasources.LocalDatasourceChart
import com.gabez.yet_another_currency_converter.chart.data.datasources.RemoteDatasourceChart
import com.gabez.yet_another_currency_converter.chart.domain.ChartRepository

class ChartRepositoryImpl(private val localDatasource: LocalDatasourceChart, private val remoteDatasource: RemoteDatasourceChart): ChartRepository {
    override suspend fun getFavouriteCurrencies(): List<CurrencyUniversal> = localDatasource.getFavouriteCurrencies()

    //TODO: Check internet connection
    override suspend fun getRates(
        code: String,
        currencyName: String,
        dateFrom: String,
        dateTo: String
    ): GetRatesResponse = remoteDatasource.getRates(code, currencyName, dateFrom, dateTo)
}