package com.gabez.yet_another_currency_converter.data.apiService.responses

data class CurrencyTableFromApi(val table: String, val no: String, val effectiveDate: String, val rates: List<RateForTable>)