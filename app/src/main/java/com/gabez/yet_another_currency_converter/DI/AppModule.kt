package com.gabez.yet_another_currency_converter.DI

import com.gabez.yet_another_currency_converter.app.calculator.CalculatorViewModel
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.SelectCurrencyViewModel
import com.gabez.yet_another_currency_converter.domain.usecases.CalculateUsecase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CalculatorViewModel(get()) }
    viewModel { SelectCurrencyViewModel() }

    single{ CalculateUsecase() }
}