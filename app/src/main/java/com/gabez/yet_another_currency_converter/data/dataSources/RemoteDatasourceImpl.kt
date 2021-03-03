package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.common.GetCurrenciesResponse

class RemoteDatasourceImpl(private val facade: ApiFacade): RemoteDatasource {
    override suspend fun getCurrencies(): GetCurrenciesResponse = facade.getCurrencies()
}