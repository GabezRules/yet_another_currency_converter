package com.gabez.data_access.entities

import java.util.*

data class RateUniversal(val parentCurrencyName: String = "",
                         val parentCode: String = "",
                         val no: String,
                         val effectiveDate: Date,
                         val mid: Float)