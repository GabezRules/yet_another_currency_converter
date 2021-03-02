package com.gabez.yet_another_currency_converter.DI

import com.gabez.yet_another_currency_converter.app.calculator.CalculatorViewModel
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.SelectCurrencyViewModel
import com.gabez.yet_another_currency_converter.data.CalculatorRepositoryImpl
import com.gabez.yet_another_currency_converter.domain.CalculatorRepository
import com.gabez.yet_another_currency_converter.domain.usecases.CalculateUsecase
import com.gabez.yet_another_currency_converter.domain.usecases.GetAllCurrenciesUsecase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
    viewModel { SelectCurrencyViewModel(get()) }

    single{ CalculateUsecase(get()) }
    single{ GetAllCurrenciesUsecase(get()) }
    single{ CalculatorRepositoryImpl() as CalculatorRepository }
}