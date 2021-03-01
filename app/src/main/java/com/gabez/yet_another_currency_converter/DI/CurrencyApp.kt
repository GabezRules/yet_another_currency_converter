package com.gabez.yet_another_currency_converter.DI

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class CurrencyApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }


    private fun initKoin() {
        startKoin {
            androidContext(this@CurrencyApp)
            modules(appModule)
        }
    }
}