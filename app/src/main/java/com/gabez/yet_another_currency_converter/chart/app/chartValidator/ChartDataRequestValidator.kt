package com.gabez.yet_another_currency_converter.chart.app.chartValidator

import java.text.SimpleDateFormat

class ChartDataRequestValidator {
    companion object{
        fun isValid(request: ChartDataRequest): ChartDataRequestValidatorResponse{
            val response = ChartDataRequestValidatorResponse(
                isFirstDateValid = isDateValid(request.firstDate),
                isSecondDateValid = isDateValid(request.secondDate),
                isCurrencyValid = (!request.currencyName.isNullOrEmpty()),
            )

            if(response.isFirstDateValid && response.isSecondDateValid)
                response.isChronologyValid = isChronologyValid(request.firstDate, request.secondDate)

            if(response.isFirstDateValid && response.isSecondDateValid && response.isCurrencyValid && response.isChronologyValid) response.isValid = true
            return response
        }

        private fun isDateValid(date: String): Boolean{
            return try{
                SimpleDateFormat("yyyy-MM-dd").parse(date)
                true
            }catch (e: Exception){
                false
            }
        }

        private fun isChronologyValid(first: String, second: String): Boolean{
            val firstFate = SimpleDateFormat("yyyy-MM-dd").parse(first)
            val secondDate = SimpleDateFormat("yyyy-MM-dd").parse(second)
            return firstFate.after(secondDate)
        }
    }
}