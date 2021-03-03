package com.gabez.nbp_api.apiService.entities

data class CurrencyTableFromAPI(val table: String, val no: String, val effectiveDate: String, val rates: List<RateInTableFromAPI>)