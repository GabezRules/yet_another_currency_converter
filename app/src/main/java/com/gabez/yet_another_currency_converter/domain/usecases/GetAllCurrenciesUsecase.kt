package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor

class GetAllCurrenciesUsecase(private val repo: CalculatorRepository, private val connectionMonitor: InternetConnectionMonitor) {
    suspend fun invoke(): GetAllCurrenciesResponse = repo.getAllCurrencies(connectionMonitor.value!!)
}