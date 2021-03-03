package com.gabez.yet_another_currency_converter.chart.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequest
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidator
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidatorResponse
import com.gabez.yet_another_currency_converter.chart.domain.GetChartDataUsecase
import com.gabez.yet_another_currency_converter.chart.domain.GetFavouriteCurrenciesUsecase

class ChartViewModel(
    private val getDataUsecase: GetChartDataUsecase,
    private val getFavsUsecase: GetFavouriteCurrenciesUsecase
) : ViewModel() {
    private val dateFrom: MutableLiveData<String> = MutableLiveData("yyyy-MM-dd")
    private val dateTo: MutableLiveData<String> = MutableLiveData("yyyy-MM-dd")
    private val currency: MutableLiveData<CurrencyForView?> = MutableLiveData()

    fun setDateTo(date: String){
        dateTo.postValue(date)
        getDataUsecase.dateTo = date
    }

    fun setDateFrom(date: String){
        dateFrom.postValue(date)
        getDataUsecase.dateFrom = date
    }

    fun setCurrency(newCurrency: CurrencyForView) {
        currency.value = newCurrency
        getDataUsecase.currency = newCurrency
    }

    fun getChartData(): ChartDataRequestValidatorResponse {
        val request = ChartDataRequest(
            firstDate = dateFrom.value!!,
            secondDate = dateTo.value!!,
            currencyName = currency.value?.currencyName
        )

        val validatorResponse = ChartDataRequestValidator.isValid(request)
        if (validatorResponse.isValid) {
            return validatorResponse
        } else {
            return validatorResponse
        }
    }
}