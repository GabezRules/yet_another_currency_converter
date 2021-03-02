package com.gabez.yet_another_currency_converter.data.apiService.entities

data class CurrencyTableFromAPI(val table: String, val no: String, val effectiveDate: String, val rates: List<RateInTableFromAPI>)