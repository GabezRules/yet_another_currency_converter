package com.gabez.yet_another_currency_converter.calculator.app.selectFromAllCurrencies.currencyList

import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

interface CurrencyListCallback {
    fun markCurrency(currency: CurrencyForView)
    fun unmarkCurrency(currency: CurrencyForView)
    fun setCurrency(currency: CurrencyForView)
}