package com.gabez.yet_another_currency_converter.data.dataSources

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.localDbFacade.LocalDbFacade
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class LocalDatasourceImpl(private val facade: LocalDbFacade): LocalDatasource {

    override suspend fun getCurrencies(): GetCurrenciesResponse {
        return facade.getCurrencies()
    }

    override suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView) {
        facade.markCurrency(currency.currencyName, isFavourite)
    }
}