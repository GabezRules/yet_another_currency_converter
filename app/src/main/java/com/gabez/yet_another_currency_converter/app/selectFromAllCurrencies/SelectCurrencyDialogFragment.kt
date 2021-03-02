package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList.AllCurrenciesListAdapter
import com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList.CurrencyListCallback
import com.gabez.yet_another_currency_converter.entities.CurrencyForView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.gabez.yet_another_currency_converter.data.apiService.responses.ResponseStatus
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class SelectCurrencyDialogFragment(
    private val callback: SelectCurrencyDialogCallback,
    private val spinnerIndex: CurrencySpinnerIndex
) : DialogFragment(),
    CurrencyListCallback, KoinComponent {

    lateinit var allCurrenciesRecyclerView: RecyclerView
    private lateinit var searchBar: TextInputEditText
    private lateinit var nothingFoundText: TextView
    private lateinit var closeButton: ExtendedFloatingActionButton
    lateinit var progressBar: ProgressBar

    lateinit var adapter: AllCurrenciesListAdapter

    private val viewModel: SelectCurrencyViewModel by inject()

    private var currencyList: MutableLiveData<List<CurrencyForView>> = MutableLiveData(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_currencies_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        val params: android.view.WindowManager.LayoutParams = dialog!!.window!!.attributes
        params.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        params.height = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        dialog!!.window!!.attributes = params
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        getCurrencyList()
        initRecyclerView()
        observeLoading()
        setupSearch()
        observeCurrencyList()
        setupCloseClick()
    }

    private fun initViews(view: View) {
        allCurrenciesRecyclerView = view.findViewById(R.id.searchRecyclerView)
        searchBar = view.findViewById(R.id.searchBody)
        nothingFoundText = view.findViewById(R.id.nothingFound)
        progressBar = view.findViewById(R.id.progressBar)
        closeButton = view.findViewById(R.id.closeButton)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private fun getCurrencyList() {
        nothingFoundText.text = "Nothing found..."
        Log.v("CURRENCIES", "starting...")
        GlobalScope.launch {
            viewModel.getCurrencies().collect { response ->
                when(response.flag){
                    ResponseStatus.SUCCESS -> {
                        currencyList.postValue(response.data as List<CurrencyForView>)
                        Log.v("CURRENCIES", response.data.toString())
                        (response.data as List<CurrencyForView>).map { item -> Log.v("CURRENCIES", item.nameShort) }
                    }
                    ResponseStatus.FAILED -> nothingFoundText.text = response.data.toString()
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = AllCurrenciesListAdapter(this@SelectCurrencyDialogFragment)
        adapter.setData(currencyList.value!!)

        allCurrenciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        allCurrenciesRecyclerView.adapter = adapter
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                allCurrenciesRecyclerView.visibility = View.INVISIBLE
                nothingFoundText.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                allCurrenciesRecyclerView.visibility = View.VISIBLE
                nothingFoundText.visibility = View.VISIBLE
            }
        })
    }

    private fun setupSearch() {
        searchBar.doOnTextChanged { text, _, _, _ ->
            val newList: List<CurrencyForView> = currencyList.value!!.filter { currencyItem ->
                (getUniversalText(currencyItem.nameShort).contains(getUniversalText(text))
                        || getUniversalText(currencyItem.nameLong).contains(getUniversalText(text)))
            }

            currencyList.value = newList
        }
    }

    private fun getUniversalText(text: Any?): String = text.toString().toLowerCase()

    private fun observeCurrencyList() {
        currencyList.observe(viewLifecycleOwner, { list ->
            adapter.setData(list)
            nothingFoundText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    private fun setupCloseClick() {
        closeButton.setOnClickListener { this@SelectCurrencyDialogFragment.dismiss() }
    }

    override fun markCurrency(currency: CurrencyForView) = viewModel.markCurrency(currency)

    override fun unmarkCurrency(currency: CurrencyForView) = viewModel.unmarkCurrency(currency)

    override fun setCurrency(currency: CurrencyForView) {
        callback.setCurrency(currency, spinnerIndex)
        this@SelectCurrencyDialogFragment.dismiss()
    }
}