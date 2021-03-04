package com.gabez.yet_another_currency_converter.chart.domain.usecases

import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.chart.domain.ChartRepository

class GetFavouriteCurrenciesUsecase(private val repo: ChartRepository) {
    suspend fun invoke(): List<CurrencyUniversal> = repo.getFavouriteCurrencies()
}