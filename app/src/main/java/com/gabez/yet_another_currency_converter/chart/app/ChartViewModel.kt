package com.gabez.yet_another_currency_converter.chart.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequest
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidator
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidatorResponse
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetChartDataUsecase
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetFavouriteCurrenciesUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class ChartViewModel(
    private val getDataUsecase: GetChartDataUsecase,
    private val getFavsUsecase: GetFavouriteCurrenciesUsecase
) : ViewModel() {

    private val _dateFrom: MutableLiveData<String> = MutableLiveData()
     val dateFrom: LiveData<String> = _dateFrom

    private val _dateTo: MutableLiveData<String> = MutableLiveData()
     val dateTo: MutableLiveData<String> = _dateTo

    private val _currency: MutableLiveData<CurrencyForView?> = MutableLiveData()
     val currency: LiveData<CurrencyForView?> = _currency

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDateTo(date: String){
        _dateTo.postValue(date)
        getDataUsecase.dateTo = date
    }

    fun setDateFrom(date: String){
        _dateFrom.postValue(date)
        getDataUsecase.dateFrom = date
    }

    fun setCurrency(newCurrency: CurrencyForView) {
        _currency.value = newCurrency
        getDataUsecase.currency = newCurrency
    }

    fun getChartData(): ChartDataRequestValidatorResponse {
        val request = ChartDataRequest(
            firstDate = _dateFrom.value!!,
            secondDate = _dateTo.value!!,
            currencyName = _currency.value?.currencyName
        )

        val validatorResponse = ChartDataRequestValidator.isValid(request)
        if (validatorResponse.isValid) {
            return validatorResponse
        } else {
            return validatorResponse
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getFavouriteCurrencies(): Flow<List<CurrencyUniversal>> = channelFlow {
        _isLoading.postValue(true)
        val response = getFavsUsecase.invoke()

        _isLoading.postValue(false)
        sendBlocking(response)

        awaitClose()
    }
}