package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class SelectCurrencyViewModel: ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun markCurrency(currency: CurrencyForView){

    }

    fun unmarkCurrency(currency: CurrencyForView){

    }

    fun getCurrencies(): List<CurrencyForView> = mockCurrencyList
}