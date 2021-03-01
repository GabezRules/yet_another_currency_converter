package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList.AllCurrenciesListAdapter
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList.CurrencyListCallback
import com.google.android.material.textfield.TextInputEditText

class SelectCurrencyDialogFragment(private val callback: SelectCurrencyDialogCallback, private val spinnerIndex: CurrencySpinnerIndex) : DialogFragment(),
    CurrencyListCallback {

    lateinit var allCurrenciesRecyclerView: RecyclerView
    private lateinit var searchBar: TextInputEditText
    private lateinit var nothingFoundText: TextView
    lateinit var progressBar: ProgressBar

    lateinit var adapter: AllCurrenciesListAdapter

    private val viewModel = SelectCurrencyViewModel()

    private var currencyList: MutableLiveData<List<CurrencyForView>> = MutableLiveData(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currencies_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        getCurrencyList()
        initRecyclerView()
        observeLoading()
        setupSearch()
        observeCurrencyList()
    }

    private fun initViews(view: View) {
        allCurrenciesRecyclerView = view.findViewById(R.id.searchRecyclerView)
        searchBar = view.findViewById(R.id.searchBody)
        nothingFoundText = view.findViewById(R.id.nothingFound)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun getCurrencyList() {
        currencyList.value = viewModel.getCurrencies()
    }

    private fun initRecyclerView() {
        adapter = AllCurrenciesListAdapter(this@SelectCurrencyDialogFragment)
        adapter.setData(currencyList.value!!)

        allCurrenciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        allCurrenciesRecyclerView.adapter = adapter
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                allCurrenciesRecyclerView.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                allCurrenciesRecyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun setupSearch(){
        searchBar.doOnTextChanged { text, _, _, _ ->
            val newList: List<CurrencyForView> = currencyList.value!!.filter { currencyItem ->
                (getUniversalText(currencyItem.nameShort).contains(getUniversalText(text))
                        || getUniversalText(currencyItem.nameLong).contains(getUniversalText(text)))
            }

            currencyList.value = newList
        }
    }

    private fun getUniversalText(text: Any?): String = text.toString().toLowerCase()

    private fun observeCurrencyList(){
        currencyList.observe(viewLifecycleOwner, {
            list ->
            adapter.setData(list)
            nothingFoundText.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun markCurrency(currency: CurrencyForView) = viewModel.markCurrency(currency)

    override fun unmarkCurrency(currency: CurrencyForView) = viewModel.unmarkCurrency(currency)

    override fun setCurrency(currency: CurrencyForView){
        callback.setCurrency(currency, spinnerIndex)
        this@SelectCurrencyDialogFragment.dismiss()
    }
}