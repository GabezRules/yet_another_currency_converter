package com.gabez.yet_another_currency_converter.data.apiService.responses

import com.gabez.yet_another_currency_converter.data.apiService.entities.CurrencyFromAPI
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

data class GetAllCurrenciesResponse(val flag: ResponseStatus, val data: List<CurrencyForView>? = null, var error: Any? = null)