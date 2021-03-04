package com.gabez.yet_another_currency_converter.chart.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.data_access.common.GetRatesResponse
import com.gabez.data_access.common.ResponseStatus
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequest
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidator
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetChartDataUsecase
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetFavouriteCurrenciesUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

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

    fun setDateTo(date: String) {
        _dateTo.postValue(date)
        getDataUsecase.dateTo = date
    }

    fun setDateFrom(date: String) {
        _dateFrom.postValue(date)
        getDataUsecase.dateFrom = date
    }

    fun setCurrency(newCurrency: CurrencyForView) {
        _currency.value = newCurrency
        getDataUsecase.currency = newCurrency
    }

    @ExperimentalCoroutinesApi
    fun getChartData(): Flow<GetRatesResponse> = channelFlow {
        /*
        val request = ChartDataRequest(
            firstDate = _dateFrom.value!!,
            secondDate = _dateTo.value!!,
            currencyName = _currency.value?.currencyName
        )

         */

        val request = ChartDataRequest(
            firstDate = "2020-02-02",
            secondDate = "2020-01-01",
            currencyName = "usd"
        )

        val validatorResponse = ChartDataRequestValidator.isValid(request)
        if (validatorResponse.isValid) {
            val response = getDataUsecase.invoke()
            launch {
                _isLoading.postValue(false)
                sendBlocking(response)
            }
        } else {
            launch {
                _isLoading.postValue(false)
                sendBlocking(
                    GetRatesResponse(
                        flag = ResponseStatus.FAILED,
                        data = null,
                        error = validatorResponse
                    )
                )
                close()
            }
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