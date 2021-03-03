package com.gabez.yet_another_currency_converter.data

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.ResponseStatus
import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.RemoteDatasource
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class CalculatorRepositoryImpl(private val localDatasource: LocalDatasource, private val remoteDatasource: RemoteDatasource) : CalculatorRepository {

    override suspend fun getAllCurrencies(hasInternetConnection: Boolean): GetCurrenciesResponse {
        return if (hasInternetConnection) {
            val remoteCurrencies = remoteDatasource.getCurrencies()
            val localFavs = localDatasource.getFavourites()

            if(remoteCurrencies.flag != ResponseStatus.FAILED){
                remoteCurrencies.data!!.map { remoteCurrency ->
                    localFavs.map {
                            localCurrency ->
                        if(remoteCurrency.currencyName == localCurrency.currencyName) remoteCurrency.isFavourite = localCurrency.isFavourite
                    }
                }
            }

            remoteCurrencies
        }
        else localDatasource.getCurrencies()
    }

    override suspend fun markCurrency(isFavourite: Boolean, currency: CurrencyForView) {
        localDatasource.markCurrency(isFavourite, currency)
    }
}