package com.gabez.yet_another_currency_converter.DI

import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.apiFacade.ApiFacadeImpl
import com.gabez.data_access.localDbFacade.LocalDbFacade
import com.gabez.data_access.localDbFacade.LocalDbFacadeImpl
import com.gabez.yet_another_currency_converter.calculator.app.calculator.CalculatorViewModel
import com.gabez.yet_another_currency_converter.calculator.app.selectFromAllCurrencies.SelectCurrencyViewModel
import com.gabez.yet_another_currency_converter.calculator.data.CalculatorRepositoryImpl
import com.gabez.nbp_api.apiService.network.NetworkClient
import com.gabez.nbp_api.apiService.network.NetworkClientImpl
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.LocalDatasource
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.LocalDatasourceImpl
import com.gabez.local_database.CurrencyDatabase
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.RemoteDatasource
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.RemoteDatasourceImpl
import com.gabez.yet_another_currency_converter.calculator.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.MarkCurrencyUsecase
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
    viewModel { SelectCurrencyViewModel(get(), get()) }

    single { GetAllCurrenciesUsecase(get(), get()) }
    single { MarkCurrencyUsecase(get()) }
    single { CalculatorRepositoryImpl(get(), get()) as CalculatorRepository }

    single { LocalDatasourceImpl(get()) as LocalDatasource }
    single { RemoteDatasourceImpl(get()) as RemoteDatasource }

    single {ApiFacadeImpl(get()) as ApiFacade}
    single { LocalDbFacadeImpl(get()) as LocalDbFacade }

    single { NetworkClientImpl() as NetworkClient }
    single { CurrencyDatabase.getInstance(get()) }

    single { InternetConnectionMonitor(get()) }
}