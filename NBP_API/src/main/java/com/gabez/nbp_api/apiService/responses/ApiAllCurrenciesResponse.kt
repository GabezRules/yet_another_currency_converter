package com.gabez.nbp_api.apiService.responses

import com.gabez.nbp_api.apiService.entities.CurrencyFromAPI

data class ApiAllCurrenciesResponse(val flag: ApiResponseStatus, val data: List<CurrencyFromAPI>? = null, var error: Any? = null)