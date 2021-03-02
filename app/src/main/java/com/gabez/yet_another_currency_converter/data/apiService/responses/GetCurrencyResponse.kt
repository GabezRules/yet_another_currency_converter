package com.gabez.yet_another_currency_converter.data.apiService.responses

import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyFromAPI

data class GetCurrencyResponse(val status: ResponseStatus, val data: CurrencyFromAPI? = null, val error: Any? = null)