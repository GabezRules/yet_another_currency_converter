package com.gabez.data_access.common

import com.gabez.data_access.entities.CurrencyUniversal

data class GetCurrenciesResponse(val flag: ResponseStatus, val data: List<CurrencyUniversal>? = null, var error: Any? = null)