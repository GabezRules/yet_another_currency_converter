package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import com.gabez.yet_another_currency_converter.entities.CurrencyForView

interface SelectCurrencyDialogCallback {
    fun setCurrency(currency: CurrencyForView, spinnerIndex: CurrencySpinnerIndex)
}