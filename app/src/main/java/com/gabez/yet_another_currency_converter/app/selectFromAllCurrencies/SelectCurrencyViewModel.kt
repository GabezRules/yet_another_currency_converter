package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.data.entities.CurrencyFromApi
import com.gabez.yet_another_currency_converter.domain.response.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class SelectCurrencyViewModel(private val usecase: GetAllCurrenciesUsecase) : ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun markCurrency(currency: CurrencyForView) {

    }

    fun unmarkCurrency(currency: CurrencyForView) {

    }

    @ExperimentalCoroutinesApi
    suspend fun getCurrencies(): Flow<GetAllCurrenciesResponse> = channelFlow {

        _isLoading.postValue(true)
        val response = usecase.invoke()

        when (response.flag) {
            ResponseStatus.FAILED -> launch {
                _isLoading.postValue(false)
                sendBlocking(response)
                close()
            }
            ResponseStatus.SUCCESS -> launch {
                _isLoading.postValue(false)
                val mappedResponse = GetAllCurrenciesResponse(
                    flag = ResponseStatus.SUCCESS,
                    data = (response.data as List<CurrencyFromApi>).map { currencyFromApi -> currencyFromApi.toCurrencyForView() }
                )
                sendBlocking(mappedResponse)
                close()
            }
            else -> launch {
                _isLoading.postValue(false)
                sendBlocking(response)
                close()
            }
        }

        awaitClose()

    }

    fun CurrencyFromApi.toCurrencyForView(): CurrencyForView {
        return CurrencyForView(
            nameShort = this.currency,
            nameLong = this.code
        )
    }
}