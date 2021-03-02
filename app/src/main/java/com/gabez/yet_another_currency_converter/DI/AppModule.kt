package com.gabez.yet_another_currency_converter.DI

import com.gabez.yet_another_currency_converter.app.calculator.CalculatorViewModel
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.SelectCurrencyViewModel
import com.gabez.yet_another_currency_converter.data.CalculatorRepositoryImpl
import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClient
import com.gabez.yet_another_currency_converter.data.apiService.network.NetworkClientImpl
import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.data.dataSources.LocalDatasourceImpl
import com.gabez.yet_another_currency_converter.data.localDb.CurrencyDatabase
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.usecases.CalculateUsecase
import com.gabez.yet_another_currency_converter.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
    viewModel { SelectCurrencyViewModel(get()) }

    single { GetAllCurrenciesUsecase(get(), get()) }
    single { CalculatorRepositoryImpl(get()) as CalculatorRepository }

    single { LocalDatasourceImpl(get()) as LocalDatasource }
    single { NetworkClientImpl() as NetworkClient }

    single { CurrencyDatabase.getInstance(get()) }

    single { InternetConnectionMonitor(get()) }
}