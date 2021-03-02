package com.gabez.yet_another_currency_converter.data.dataSources.providers

import com.gabez.yet_another_currency_converter.data.dataSources.RemoteDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.RemoteDatasourceImpl

class RemoteDatasourceProvider {
    companion object {
        fun provideRemoteDatasource(): RemoteDatasource = RemoteDatasourceImpl()
    }
}