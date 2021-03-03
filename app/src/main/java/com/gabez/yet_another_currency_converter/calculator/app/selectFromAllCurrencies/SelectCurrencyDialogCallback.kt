package com.gabez.yet_another_currency_converter.calculator.app.selectFromAllCurrencies

import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView

interface SelectCurrencyDialogCallback {
    fun setCurrency(currency: CurrencyForView, spinnerIndex: CurrencySpinnerIndex)
}