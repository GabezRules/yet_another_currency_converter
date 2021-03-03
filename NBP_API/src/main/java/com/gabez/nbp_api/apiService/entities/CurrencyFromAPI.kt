package com.gabez.nbp_api.apiService.entities

data class CurrencyFromAPI(val currency: String = "", val code: String = "", val rates: List<RateInCurrencyFromAPI>)