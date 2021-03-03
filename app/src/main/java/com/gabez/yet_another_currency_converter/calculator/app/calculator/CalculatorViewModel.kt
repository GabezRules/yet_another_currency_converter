package com.gabez.yet_another_currency_converter.calculator.app.calculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.calculator.app.calculator.calculateValidator.ValidateRequest
import com.gabez.yet_another_currency_converter.calculator.app.calculator.calculateValidator.CalculateRequestValidator
import com.gabez.yet_another_currency_converter.calculator.domain.calculations.CalculateResponse
import com.gabez.yet_another_currency_converter.calculator.domain.calculations.CalculateResponseStatus
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.CalculateUsecase
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor

class CalculatorViewModel(private val context: Context, private val connectionMonitor: InternetConnectionMonitor): ViewModel() {
    private var valueToCalculate: Float? = 0f

    private val _firstCurrency: MutableLiveData<CurrencyForView> = MutableLiveData()

    val firstCurrency: LiveData<CurrencyForView> = _firstCurrency

    private val _secondCurrency: MutableLiveData<CurrencyForView> = MutableLiveData()

    val secondCurrency: LiveData<CurrencyForView> = _secondCurrency

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val hasInternet: LiveData<Boolean> = connectionMonitor

    private val usecase: CalculateUsecase = CalculateUsecase()

    fun setValueToCalculate(value: Float?){
        valueToCalculate = value
        usecase.setAmount(value)
    }

    fun setFirstCurrency(value: CurrencyForView) {
        _firstCurrency.postValue(value)
        usecase.setFirstCurrency(value)
    }

    fun setSecondCurrency(value: CurrencyForView){
        _secondCurrency.postValue(value)
        usecase.setSecondCurrency(value)
    }

    fun calculate(): CalculateResponse {
        val calculateRequest = createCalculateRequest()
        val response = CalculateRequestValidator.isValid(calculateRequest)

        return if(response.isValid) usecase.invoke()
        else CalculateResponse(CalculateResponseStatus.NOT_VALID, null, response)
    }

    fun swapCurrencies(){
        val bufferValue = _firstCurrency.value
        _firstCurrency.value = _secondCurrency.value
        _secondCurrency.value = bufferValue
    }

    private fun createCalculateRequest(): ValidateRequest{
        return ValidateRequest(
            firstCurrency = _firstCurrency.value,
            secondCurrency = _secondCurrency.value,
            amount = valueToCalculate
        )
    }
}