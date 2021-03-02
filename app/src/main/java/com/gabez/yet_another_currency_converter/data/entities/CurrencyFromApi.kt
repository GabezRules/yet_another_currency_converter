package com.gabez.yet_another_currency_converter.data.entities

data class CurrencyFromApi(val currency: String = "", val code: String = "", val rates: List<RateFromApi>)