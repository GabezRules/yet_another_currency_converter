package com.gabez.nbp_api.apiService

import com.gabez.nbp_api.apiService.network.NetworkClient
import com.gabez.nbp_api.apiService.network.NetworkClientImpl

class NetworkClientProvider {
    companion object{
        fun provideNetworkClient(): NetworkClient = NetworkClientImpl()
    }
}