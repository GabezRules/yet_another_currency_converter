package com.gabez.yet_another_currency_converter.app.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequest
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidator
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidatorResponse
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class CalculatorViewModel: ViewModel() {
    private var valueToCalculate: Float? = 0f

    private val _firstCurrency: MutableLiveData<CurrencyForView> = MutableLiveData()

    val firstCurrency: LiveData<CurrencyForView> = _firstCurrency

    private val _secondCurrency: MutableLiveData<CurrencyForView> = MutableLiveData()

    val secondCurrency: LiveData<CurrencyForView> = _secondCurrency

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showInternetWarning: MutableLiveData<Boolean> = MutableLiveData(false)
    val showInternetWarning: LiveData<Boolean> = _showInternetWarning

    fun setValueToCalculate(value: Float?){
        valueToCalculate = value
    }

    fun setFirstCurrency(value: CurrencyForView) = _firstCurrency.postValue(value)

    fun setSecondCurrency(value: CurrencyForView) = _secondCurrency.postValue(value)

    fun calculate(): CalculateRequestValidatorResponse?{
        val calculateRequest = createCalculateRequest()
        val response = CalculateRequestValidator.isValid(calculateRequest)
        if(response.isValid){
            //TODO: send request to calculate
            return null
        }else return response
    }

    fun swapCurrencies(){
        val bufferValue = _firstCurrency.value
        _firstCurrency.value = _secondCurrency.value
        _secondCurrency.value = bufferValue
    }

    private fun createCalculateRequest(): CalculateRequest{
        return CalculateRequest(
            firstCurrency = _firstCurrency.value,
            secondCurrency = _secondCurrency.value,
            amount = valueToCalculate
        )
    }
}