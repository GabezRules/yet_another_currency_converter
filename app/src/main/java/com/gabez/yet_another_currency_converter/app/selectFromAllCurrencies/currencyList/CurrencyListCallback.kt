package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList

import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface CurrencyListCallback {
    fun markCurrency(currency: CurrencyForView)
    fun unmarkCurrency(currency: CurrencyForView)
    fun setCurrency(currency: CurrencyForView)
}