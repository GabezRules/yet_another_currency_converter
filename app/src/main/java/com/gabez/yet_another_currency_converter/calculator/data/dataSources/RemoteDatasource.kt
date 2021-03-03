package com.gabez.yet_another_currency_converter.calculator.data.dataSources

import com.gabez.data_access.common.GetCurrenciesResponse

interface RemoteDatasource {
    suspend fun getCurrencies(): GetCurrenciesResponse
}