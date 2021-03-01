package com.gabez.yet_another_currency_converter.domain.usecases

import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class CalculateUsecase() {
    private var firstCurrency: CurrencyForView? = null
    private var secondCurrency: CurrencyForView? = null
    private var amount: Float? = 0f

    private val repo: CalculatorRepository = CalculatorRepository.getInstance() as CalculatorRepository

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
        firstCurrencyShortName = firstCurrency!!.nameShort,
        secondCurrencyShortName = secondCurrency!!.nameShort,
        amount = amount!!
    ))
}