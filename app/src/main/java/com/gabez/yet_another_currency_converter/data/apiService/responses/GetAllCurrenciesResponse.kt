package com.gabez.yet_another_currency_converter.domain.response

import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus

data class GetAllCurrenciesResponse(val flag: ResponseStatus, val data: Any)