package com.gabez.data_access.localDbFacade

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.ResponseStatus
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.local_database.CurrencyDatabase

class LocalDbFacadeImpl(private val db: CurrencyDatabase) : LocalDbFacade {
    override suspend fun getCurrencies(): GetCurrenciesResponse {
        return GetCurrenciesResponse(
            flag = ResponseStatus.SUCCESS,
            data = db.dao().getAllCurrencies().map { currencyEntity ->
                CurrencyUniversal(
                    code = currencyEntity.code,
                    currencyName = currencyEntity.currencyName,
                    mid = currencyEntity.mid,
                    isFavourite = currencyEntity.isFavourite,
                    date = "",
                    no = ""
                )
            }
        )
    }

    override suspend fun markCurrency(currency: String, isFavourite: Boolean) {
        db.dao().setFavourite(currency, isFavourite)
    }

    override suspend fun getFavCurrencies(): List<CurrencyUniversal> {
        return db.dao().getAllFavourites().map { currencyEntity ->
            CurrencyUniversal(
                code = currencyEntity.code,
                currencyName = currencyEntity.currencyName,
                mid = currencyEntity.mid,
                isFavourite = currencyEntity.isFavourite,
                date = "",
                no = ""
            )
        }
    }
}