package com.gabez.yet_another_currency_converter.data.dataSources.providers

import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasourceImpl

class LocalDatasourceProvider {
    companion object {
        fun provideLocalDatasource(): LocalDatasource = LocalDatasourceImpl()
    }
}