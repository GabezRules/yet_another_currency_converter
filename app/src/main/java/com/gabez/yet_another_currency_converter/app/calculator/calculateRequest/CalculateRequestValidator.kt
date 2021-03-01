package com.gabez.yet_another_currency_converter.app.calculator.calculateRequest

import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class CalculateRequestValidator {
    companion object{
        fun isValid(request: CalculateRequest): CalculateRequestValidatorResponse{
            val response = CalculateRequestValidatorResponse(
                isFirstCurrencyValid = isCurrencyValid(request.firstCurrency),
                isSecondCurrencyValid = isCurrencyValid(request.secondCurrency),
                isAmountValid = isAmountValid(request.amount)
            )

            response.isValid = (response.isFirstCurrencyValid && response.isSecondCurrencyValid && response.isAmountValid)
            return response
        }

        private fun isCurrencyValid(currency: CurrencyForView?): Boolean = currency != null

        private fun isAmountValid(amount: Float?): Boolean = amount != null
    }
}