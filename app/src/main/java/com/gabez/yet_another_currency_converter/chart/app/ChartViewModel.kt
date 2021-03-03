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
    val dateFrom: MutableLiveData<String> = MutableLiveData("yyyy-mm-dd")
    val dateTo: MutableLiveData<String> = MutableLiveData("yyyy-mm-dd")
    private var currency: CurrencyForView? = null

    fun getChartData(): ChartDataRequestValidatorResponse {
        val request = ChartDataRequest(
            firstDate = dateFrom.value!!,
            secondDate = dateTo.value!!,
            currencyName = currency?.currencyName
        )

        val validatorResponse = ChartDataRequestValidator.isValid(request)
        if (validatorResponse.isValid) {
            return validatorResponse
        } else {
            return validatorResponse
        }
    }
}