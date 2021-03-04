package com.gabez.yet_another_currency_converter.calculator.data.dataSources

import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.common.GetCurrenciesResponse

class RemoteDatasourceCalculatorImpl(private val facade: ApiFacade): RemoteDatasourceCalculator {
    override suspend fun getCurrencies(): GetCurrenciesResponse = facade.getCurrencies()
}