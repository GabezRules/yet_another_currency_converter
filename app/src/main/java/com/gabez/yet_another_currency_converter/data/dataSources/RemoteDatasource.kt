package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.data_access.common.GetCurrenciesResponse

interface RemoteDatasource {
    suspend fun getCurrencies(): GetCurrenciesResponse
}