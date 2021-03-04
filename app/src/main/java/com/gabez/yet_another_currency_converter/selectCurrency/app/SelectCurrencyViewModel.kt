package com.gabez.yet_another_currency_converter.selectCurrency.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabez.data_access.common.GetCurrenciesResponse
import com.gabez.data_access.common.ResponseStatus
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.MarkCurrencyUsecase
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class SelectCurrencyViewModel(
    private val getAllUsecase: GetAllCurrenciesUsecase,
    private val markUsecase: MarkCurrencyUsecase
) : ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun markCurrency(currency: CurrencyForView) = markUsecase.invoke(true, currency)

    suspend fun unmarkCurrency(currency: CurrencyForView) = markUsecase.invoke(false, currency)

    @ExperimentalCoroutinesApi
    suspend fun getCurrencies(): Flow<GetCurrenciesResponse> = channelFlow {

        _isLoading.postValue(true)
        val response = getAllUsecase.invoke()

        launch {
            _isLoading.postValue(false)
            sendBlocking(response)
            close()
        }

        awaitClose()

    }
}