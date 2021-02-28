package com.gabez.yet_another_currency_converter.app.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabez.yet_another_currency_converter.R

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    companion object{
        fun getInstance(): CalculatorFragment = CalculatorFragment()
    }
}