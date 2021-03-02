package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.yet_another_currency_converter.data.apiService.responses.GetAllCurrenciesResponse
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.gabez.yet_another_currency_converter.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.domain.usecases.MarkCurrencyUsecase
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class SelectCurrencyViewModel(private val getAllUsecase: GetAllCurrenciesUsecase, private val markUsecase: MarkCurrencyUsecase) : ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun markCurrency(currency: CurrencyForView) = markUsecase.invoke(true, currency)

    suspend fun unmarkCurrency(currency: CurrencyForView) = markUsecase.invoke(false, currency)

    @ExperimentalCoroutinesApi
    suspend fun getCurrencies(): Flow<GetAllCurrenciesResponse> = channelFlow {

        _isLoading.postValue(true)
        val response = getAllUsecase.invoke()

        when (response.flag) {
            ResponseStatus.FAILED -> launch {
                _isLoading.postValue(false)
                sendBlocking(response)
                close()
            }
            ResponseStatus.SUCCESS -> launch {
                _isLoading.postValue(false)
                sendBlocking(response)
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
}