package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse

class GetAllCurrenciesUsecase(private val repo: CalculatorRepository) {
    suspend fun invoke(): GetAllCurrenciesResponse = repo.getAllCurrencies()
}