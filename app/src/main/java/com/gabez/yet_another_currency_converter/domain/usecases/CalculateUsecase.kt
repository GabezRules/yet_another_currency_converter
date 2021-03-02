package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class CalculateUsecase(private val repo: CalculatorRepository) {
    private var firstCurrency: CurrencyForView? = null
    private var secondCurrency: CurrencyForView? = null
    private var amount: Float? = 0f

    fun setFirstCurrency(value: CurrencyForView?){
        firstCurrency = value
    }

    fun setSecondCurrency(value: CurrencyForView?){
        secondCurrency = value
    }

    fun setAmount(value: Float?){
        amount = value
    }

    suspend fun invoke(): CalculateResponse = repo.calculate(CalculateRequest(
        firstCurrencyShortName = firstCurrency!!.code,
        secondCurrencyShortName = secondCurrency!!.code,
        amount = amount!!
    ))
}