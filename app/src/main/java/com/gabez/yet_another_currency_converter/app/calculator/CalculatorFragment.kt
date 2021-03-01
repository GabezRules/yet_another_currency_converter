package com.gabez.yet_another_currency_converter.app.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.app.calculator.calculateRequest.CalculateRequestValidatorResponse
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.CurrencySpinnerIndex
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.SelectCurrencyDialogCallback
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.SelectCurrencyDialogFragment
import com.gabez.yet_another_currency_converter.domain.response.CalculateResponseData
import com.gabez.yet_another_currency_converter.domain.response.ResponseStatus
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class CalculatorFragment : Fragment(), SelectCurrencyDialogCallback, KoinComponent {

    private lateinit var selectFirstCurrency: MaterialSpinner
    private lateinit var selectSecondCurrency: MaterialSpinner

    private lateinit var firstCurrencyAmount: TextInputEditText
    private lateinit var secondCurrencyAmount: TextInputEditText

    private lateinit var swapCurrencies: TextView

    private lateinit var calculateButton: MaterialButton

    private lateinit var progressBar: ProgressBar
    private lateinit var noInternetWarning: TextView

    private val viewModel: CalculatorViewModel by inject()

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

    private fun initViews(view: View) {
        selectFirstCurrency = view.findViewById(R.id.selectFirstCurrency)
        selectSecondCurrency = view.findViewById(R.id.selectSecondCurrency)

        firstCurrencyAmount = view.findViewById(R.id.firstCurrencyAmountBody)
        secondCurrencyAmount = view.findViewById(R.id.secondCurrencyAmountBody)

        swapCurrencies = view.findViewById(R.id.swapCurrencies)

        calculateButton = view.findViewById(R.id.calculateButton)

        progressBar = view.findViewById(R.id.progressBar)
        noInternetWarning = view.findViewById(R.id.noInternetWarning)
    }

    private fun setupCurrenciesSelection() {
        selectFirstCurrency.setOnClickListener {
            SelectCurrencyDialogFragment(this@CalculatorFragment, CurrencySpinnerIndex.FIRST)
                .show(parentFragmentManager, "SELECT_CURRENCY")
        }

        selectSecondCurrency.setOnClickListener {
            SelectCurrencyDialogFragment(this@CalculatorFragment, CurrencySpinnerIndex.SECOND)
                .show(parentFragmentManager, "SELECT_CURRENCY")
        }

        viewModel.firstCurrency.observe(viewLifecycleOwner, Observer {
            selectFirstCurrency.text = it.nameShort
            selectFirstCurrency.collapse()
        })

        viewModel.secondCurrency.observe(viewLifecycleOwner, Observer {
            selectSecondCurrency.text = it.nameShort
            selectSecondCurrency.collapse()
        })
    }

    private fun setupCurrenciesInputs() {
        firstCurrencyAmount.doOnTextChanged { text, _, _, _ ->
            firstCurrencyAmount.error = null
            viewModel.setValueToCalculate(text.toString().toFloatOrNull())
        }
    }

    private fun setupCalculateButton() {
        calculateButton.setOnClickListener {
            GlobalScope.launch {
                viewModel.calculate().collect { response ->
                    when(response.flag){
                        ResponseStatus.NOT_VALID -> requireActivity().runOnUiThread {
                            setErrorsOnNonValidData(response.data as CalculateRequestValidatorResponse)
                            Toast.makeText(requireContext(), "NOT VALID", Toast.LENGTH_LONG).show()
                        }
                        ResponseStatus.FAILED -> requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        }
                        ResponseStatus.SUCCESS -> requireActivity().runOnUiThread {
                            resetErrors()
                            Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_LONG).show()
                            setResult(response.data as CalculateResponseData)
                        }
                    }
                }
            }
        }
    }

    private fun setErrorsOnNonValidData(response: CalculateRequestValidatorResponse) {
        if (response.isAmountValid) firstCurrencyAmount.error = "enter a valid amount!"
        if (response.isFirstCurrencyValid) selectFirstCurrency.error =
            "enter a valid currency!"
        if (response.isSecondCurrencyValid) selectSecondCurrency.error =
            "enter a valid currency!"
    }

    private fun resetErrors(){
        firstCurrencyAmount.error = null
        selectFirstCurrency.error = null
        selectSecondCurrency.error = null
    }

    private fun setResult(data: CalculateResponseData) = secondCurrencyAmount.setText(data.amount.toString())

    private fun setupSwapCurrencies() {
        swapCurrencies.setOnClickListener { viewModel.swapCurrencies() }
    }

    private fun setupLoadingObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun setupInternetWarningObserver() {
        viewModel.showInternetWarning.observe(viewLifecycleOwner, { showError ->
            noInternetWarning.visibility = if (showError) View.VISIBLE else View.GONE
        })
    }

    companion object {
        fun getInstance(): CalculatorFragment = CalculatorFragment()
    }

    override fun setCurrency(currency: CurrencyForView, spinnerIndex: CurrencySpinnerIndex) {
        when (spinnerIndex) {

            CurrencySpinnerIndex.FIRST -> viewModel.setFirstCurrency(currency)

            CurrencySpinnerIndex.SECOND -> viewModel.setSecondCurrency(currency)
        }
    }
}