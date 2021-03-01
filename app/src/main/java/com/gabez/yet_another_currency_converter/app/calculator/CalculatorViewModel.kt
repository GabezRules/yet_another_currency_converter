package com.gabez.yet_another_currency_converter.app.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.ValidateRequest
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidator
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponse
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponseStatus
import com.gabez.yet_another_currency_converter.domain.usecases.CalculateUsecase
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CalculatorViewModel(private val usecase: CalculateUsecase): ViewModel() {
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

    fun calculate(): Flow<CalculateResponse> = flow {
        val calculateRequest = createCalculateRequest()
        val response = CalculateRequestValidator.isValid(calculateRequest)
        if(response.isValid){
            _isLoading.postValue(true)
            viewModelScope.launch{
                val res = usecase.invoke()
                emit(CalculateResponse(res.flag, res))
            }.invokeOnCompletion { _isLoading.postValue(false) }
        }else emit(CalculateResponse(CalculateResponseStatus.NOT_VALID, response))
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