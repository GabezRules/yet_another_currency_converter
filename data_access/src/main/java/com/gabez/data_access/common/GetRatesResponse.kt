package com.gabez.data_access.common

import com.gabez.data_access.entities.RateUniversal

data class GetRatesResponse(val flag: ResponseStatus, val data: List<RateUniversal>? = null, val error: Any? = null)