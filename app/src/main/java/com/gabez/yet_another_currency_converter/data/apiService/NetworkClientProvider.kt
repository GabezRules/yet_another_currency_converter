package com.gabez.yet_another_currency_converter.data.apiService

class NetworkClientProvider {
    companion object{
        fun provideNetworkClient(): NetworkClient = NetworkClientImpl()
    }
}