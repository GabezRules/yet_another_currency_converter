package com.gabez.yet_another_currency_converter.app.calculator.calculateRequest

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

        private fun isCurrencyValid(currency: String?): Boolean{
            if(currency == null) return false
            if(currency.replace("\\s".toRegex(), "") == "") return false
            return true
        }

        private fun isAmountValid(amount: Float?): Boolean = amount != null
    }
}