package com.gabez.nbp_api.apiService.responses

import com.gabez.nbp_api.apiService.entities.CurrencyFromAPI

data class ApiGetCurrencyResponse(val status: ApiResponseStatus, val data: CurrencyFromAPI? = null, val error: Any? = null)