package com.gabez.yet_another_currency_converter.DI

import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.apiFacade.ApiFacadeImpl
import com.gabez.data_access.localDbFacade.LocalDbFacade
import com.gabez.data_access.localDbFacade.LocalDbFacadeImpl
import com.gabez.yet_another_currency_converter.calculator.app.CalculatorViewModel
import com.gabez.yet_another_currency_converter.selectCurrency.app.SelectCurrencyViewModel
import com.gabez.yet_another_currency_converter.calculator.data.CalculatorRepositoryImpl
import com.gabez.nbp_api.apiService.network.NetworkClient
import com.gabez.nbp_api.apiService.network.NetworkClientImpl
import com.gabez.local_database.CurrencyDatabase
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.LocalDatasourceCalculator
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.LocalDatasourceCalculatorImpl
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.RemoteDatasourceCalculator
import com.gabez.yet_another_currency_converter.calculator.data.dataSources.RemoteDatasourceCalculatorImpl
import com.gabez.yet_another_currency_converter.calculator.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.GetAllCurrenciesUsecase
import com.gabez.yet_another_currency_converter.calculator.domain.usecases.MarkCurrencyUsecase
import com.gabez.yet_another_currency_converter.chart.app.ChartViewModel
import com.gabez.yet_another_currency_converter.chart.data.ChartRepositoryImpl
import com.gabez.yet_another_currency_converter.chart.data.datasources.LocalDatasourceChart
import com.gabez.yet_another_currency_converter.chart.data.datasources.LocalDatasourceChartImpl
import com.gabez.yet_another_currency_converter.chart.data.datasources.RemoteDatasourceChart
import com.gabez.yet_another_currency_converter.chart.data.datasources.RemoteDatasourceChartImpl
import com.gabez.yet_another_currency_converter.chart.domain.ChartRepository
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetChartDataUsecase
import com.gabez.yet_another_currency_converter.chart.domain.usecases.GetFavouriteCurrenciesUsecase
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
    viewModel { SelectCurrencyViewModel(get(), get()) }
    viewModel { ChartViewModel(get(), get()) }

    single { GetAllCurrenciesUsecase(get(), get()) }
    single { MarkCurrencyUsecase(get()) }
    single { GetFavouriteCurrenciesUsecase(get()) }
    single { GetChartDataUsecase(get()) }

    single { CalculatorRepositoryImpl(get(), get()) as CalculatorRepository }
    single { ChartRepositoryImpl(get(), get()) as ChartRepository }

    single { LocalDatasourceCalculatorImpl(get()) as LocalDatasourceCalculator }
    single { RemoteDatasourceCalculatorImpl(get()) as RemoteDatasourceCalculator }

    single { LocalDatasourceChartImpl(get()) as LocalDatasourceChart }
    single { RemoteDatasourceChartImpl(get()) as RemoteDatasourceChart }

    single {ApiFacadeImpl(get()) as ApiFacade}
    single { LocalDbFacadeImpl(get()) as LocalDbFacade }

    single { NetworkClientImpl() as NetworkClient }
    single { CurrencyDatabase.getInstance(get()) }

    single { InternetConnectionMonitor(get()) }
}