package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import com.gabez.yet_another_currency_converter.entities.CurrencyForView

val mockCurrencyList = listOf(
    CurrencyForView(
        nameShort = "PLN",
        nameLong = "Polski złoty",
        isFavourite = true,
        mid = 1.234f
    ),

    CurrencyForView(
        nameShort = "USD",
        nameLong = "Dolar amerykański",
        isFavourite = false,
        mid = 0.567f
    ),

    CurrencyForView(
        nameShort = "AUD",
        nameLong = "Dolar australijski",
        isFavourite = true,
        mid = 0.5f
    ),

    CurrencyForView(
        nameShort = "THB",
        nameLong = "bat (Tajlandia)",
        isFavourite = false,
        mid = 1.212f
    ),
)