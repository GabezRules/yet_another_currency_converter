package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor

class GetAllCurrenciesUsecase(private val repo: CalculatorRepository, private val connectionMonitor: InternetConnectionMonitor) {
    suspend fun invoke(): GetCurrenciesResponse = repo.getAllCurrencies(connectionMonitor.value!!)
}