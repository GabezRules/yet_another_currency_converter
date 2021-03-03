package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class MarkCurrencyUsecase(private val repo: CalculatorRepository) {
    suspend fun invoke(isFavourite: Boolean, currency: CurrencyForView) = repo.markCurrency(isFavourite, currency)
}