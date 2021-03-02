package com.gabez.yet_another_currency_converter.data.apiService

import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClient
import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClientImpl

class NetworkClientProvider {
    companion object{
        fun provideNetworkClient(): NetworkClient = NetworkClientImpl()
    }
}