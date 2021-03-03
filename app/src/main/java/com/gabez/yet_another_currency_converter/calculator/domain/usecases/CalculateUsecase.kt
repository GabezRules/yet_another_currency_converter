package com.gabez.yet_another_currency_converter.calculator.domain.usecases

import android.util.Log
import com.gabez.yet_another_currency_converter.calculator.domain.calculations.CalculateResponse
import com.gabez.yet_another_currency_converter.calculator.domain.calculations.CalculateResponseStatus
import com.gabez.yet_another_currency_converter.calculator.domain.calculations.CalculationsHelper
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

class CalculateUsecase {
    private var firstCurrency: CurrencyForView? = null
    private var secondCurrency: CurrencyForView? = null
    private var amount: Float? = 0f

    fun setFirstCurrency(value: CurrencyForView?){
        firstCurrency = value
        Log.v("CALCULATE", "First currency mid: "+value?.mid.toString())
    }

    fun setSecondCurrency(value: CurrencyForView?){
        secondCurrency = value
        Log.v("CALCULATE", "Second currency mid: "+value?.mid.toString())
    }

    fun setAmount(value: Float?){
        amount = value
        Log.v("CALCULATE", "Amount: "+value?.toString())
    }

    fun invoke(): CalculateResponse {

        val result = CalculationsHelper.getResult(
            amount!!,
            firstCurrency!!.mid,
            secondCurrency!!.mid
        )

        return CalculateResponse(
            flag = CalculateResponseStatus.VALID,
            amount = result,
            error = null
        )
    }
}