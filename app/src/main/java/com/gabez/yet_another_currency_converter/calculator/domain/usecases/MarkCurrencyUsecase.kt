package com.gabez.yet_another_currency_converter.calculator.domain.usecases

import com.gabez.yet_another_currency_converter.calculator.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

class MarkCurrencyUsecase(private val repo: CalculatorRepository) {
    suspend fun invoke(isFavourite: Boolean, currency: CurrencyForView) = repo.markCurrency(isFavourite, currency)
}