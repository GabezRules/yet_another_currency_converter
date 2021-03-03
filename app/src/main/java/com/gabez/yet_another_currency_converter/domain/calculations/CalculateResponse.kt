package com.gabez.yet_another_currency_converter.domain.calculations

import com.gabez.nbp_api.apiService.responses.ApiResponseStatus

data class CalculateResponse(val flag: ApiResponseStatus, val amount: Float?, val error: Any?)