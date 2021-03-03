package com.gabez.yet_another_currency_converter.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.app.calculator.CalculatorFragment
import com.gabez.yet_another_currency_converter.service.GetDataService
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetDataService.startService(this@MainActivity)
        setCurrentFragment(CalculatorFragment.getInstance())

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,fragment)
            commit()
        }

    override fun onDestroy() {
        super.onDestroy()
        GetDataService.stopService(this@MainActivity)
    }
}