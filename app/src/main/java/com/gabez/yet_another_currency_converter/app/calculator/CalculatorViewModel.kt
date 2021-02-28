package com.gabez.yet_another_currency_converter.app.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequest
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidator
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidatorResponse

class CalculatorViewModel: ViewModel() {
    private var valueToCalculate: Float? = 0f

    private val _firstCurrency: MutableLiveData<String> = MutableLiveData("PLN")
    val firstCurrency: LiveData<String> = _firstCurrency

    private val _secondCurrency: MutableLiveData<String> = MutableLiveData("PLN")
    val secondCurrency: LiveData<String> = _secondCurrency

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showInternetWarning: MutableLiveData<Boolean> = MutableLiveData(false)
    val showInternetWarning: LiveData<Boolean> = _showInternetWarning

    fun setValueToCalculate(value: Float?){
        valueToCalculate = value
    }

    fun setFirstCurrency(value: String) = _firstCurrency.postValue(value)

    fun setSecondCurrency(value: String) = _secondCurrency.postValue(value)

    fun calculate(): CalculateRequestValidatorResponse?{
        val calculateRequest = createCalculateRequest()
        val response = CalculateRequestValidator.isValid(calculateRequest)
        if(response.isValid){
            /* send request to calculate */
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