package com.gabez.yet_another_currency_converter.data.apiService.entities

data class CurrencyFromAPI(val currency: String = "", val code: String = "", val rates: List<RateInCurrencyFromAPI>)