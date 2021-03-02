package com.gabez.yet_another_currency_converter.domain.usecases

import android.util.Log
import com.gabez.yet_another_currency_converter.data.apiService.responses.CalculateResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.calculations.CalculationsHelper
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

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


    //TODO: Refactor to not be dependent on repo
    fun invoke(): CalculateResponse {

        val result = CalculationsHelper.getResult(
            amount!!,
            firstCurrency!!.mid,
            secondCurrency!!.mid
        )

        return CalculateResponse(
            flag = ResponseStatus.SUCCESS,
            amount = result,
            error = null
        )
    }
}