package com.gabez.yet_another_currency_converter.app.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabez.yet_another_currency_converter.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.jaredrummler.materialspinner.MaterialSpinner

class CalculatorFragment : Fragment() {

    private lateinit var selectFirstCurrency: MaterialSpinner
    private lateinit var selectSecondCurrency: MaterialSpinner

    private lateinit var firstCurrencyAmount: TextInputEditText
    private lateinit var secondCurrencyAmount: TextInputEditText

    private lateinit var swapCurrencies: TextView

    private lateinit var calculateButton: MaterialButton

    private lateinit var progressBar: ProgressBar
    private lateinit var noInternetWarning: TextView

    val viewModel = CalculatorViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        setupCurrenciesInputs()
        setupCurrenciesSelection()
        setupCalculateButton()
        setupSwapCurrencies()
        setupLoadingObserver()
        setupInternetWarningObserver()
    }

    private fun initViews(view: View){
        selectFirstCurrency = view.findViewById(R.id.selectFirstCurrency)
        selectSecondCurrency = view.findViewById(R.id.selectSecondCurrency)

        firstCurrencyAmount = view.findViewById(R.id.firstCurrencyAmountBody)
        secondCurrencyAmount = view.findViewById(R.id.secondCurrencyAmountBody)

        swapCurrencies = view.findViewById(R.id.swapCurrencies)

        calculateButton = view.findViewById(R.id.calculateButton)

        progressBar = view.findViewById(R.id.progressBar)
        noInternetWarning = view.findViewById(R.id.noInternetWarning)
    }

    private fun setupCurrenciesSelection(){
        selectFirstCurrency.setOnClickListener {
            //viewModel.setFirstCurrency("PLN")
        }

        selectSecondCurrency.setOnClickListener {
            //viewModel.setSecondCurrency("PLN")
        }

        viewModel.firstCurrency.observe(viewLifecycleOwner, Observer{
            selectFirstCurrency.setItems(listOf(it))
        })

        viewModel.secondCurrency.observe(viewLifecycleOwner, Observer{
            selectSecondCurrency.setItems(listOf(it))
        })
    }

    private fun setupCurrenciesInputs(){
        firstCurrencyAmount.doOnTextChanged { text, start, before, count ->
            firstCurrencyAmount.error = null
            viewModel.setValueToCalculate(text.toString().toFloatOrNull())
        }
    }

    private fun setupCalculateButton(){
        calculateButton.setOnClickListener {
            val response = viewModel.calculate()
            if(response != null){
                if(!response.isAmountValid) firstCurrencyAmount.error = "enter a valid amount!"
                if(!response.isFirstCurrencyValid) selectFirstCurrency.error = "enter a valid currency!"
                if(!response.isSecondCurrencyValid) selectSecondCurrency.error = "enter a valid currency!"
            }
        }
    }

    private fun setupSwapCurrencies(){
        swapCurrencies.setOnClickListener { viewModel.swapCurrencies() }
    }

    private fun setupLoadingObserver(){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer{ isLoading ->
            progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun setupInternetWarningObserver(){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer{ showErrow ->
            noInternetWarning.visibility = if(showErrow) View.VISIBLE else View.GONE
        })
    }

    companion object{
        fun getInstance(): CalculatorFragment = CalculatorFragment()
    }
}