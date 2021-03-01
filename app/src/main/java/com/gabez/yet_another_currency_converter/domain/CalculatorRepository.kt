package com.gabez.yet_another_currency_converter.domain

import com.gabez.yet_another_currency_converter.domain.request.CalculateRequest
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse

interface CalculatorRepository {

    suspend fun calculate(request: CalculateRequest): CalculateResponse

    companion object{
        fun getInstance(): Any? = null
    }
}