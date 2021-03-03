package com.gabez.yet_another_currency_converter.chart.app.chartValidator

import java.text.SimpleDateFormat

class ChartDataRequestValidator {
    companion object{
        fun isValid(request: ChartDataRequest): ChartDataRequestValidatorResponse{
            val response = ChartDataRequestValidatorResponse(
                isFirstDateValid = isDateValid(request.firstDate),
                isSecondDateValid = isDateValid(request.secondDate),
                isCurrencyValid = (!request.currencyName.isNullOrEmpty())
            )

            if(response.isFirstDateValid && response.isSecondDateValid && response.isCurrencyValid) response.isValid = true
            return response
        }

        private fun isDateValid(date: String): Boolean{
            return try{
                val check = SimpleDateFormat("yyyy-MM-dd").parse(date)
                check != null
            }catch (e: Exception){
                false
            }
        }
    }
}