package com.gabez.yet_another_currency_converter.chart.app

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.gabez.data_access.common.ResponseStatus
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.calculator.entities.CurrencyForView
import com.gabez.yet_another_currency_converter.chart.app.chartGenerator.CustomMarker
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidatorResponse
import com.gabez.yet_another_currency_converter.chart.app.topListView.ChartCurrenciesAdapter
import com.gabez.yet_another_currency_converter.chart.entities.CurrencyForFavs
import com.gabez.yet_another_currency_converter.selectCurrency.CurrencySpinnerIndex
import com.gabez.yet_another_currency_converter.selectCurrency.SelectCurrencyDialogCallback
import com.gabez.yet_another_currency_converter.selectCurrency.app.SelectCurrencyDialogFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment : Fragment(), KoinComponent, SelectCurrencyDialogCallback {

    private lateinit var lineChart: LineChart
    private lateinit var favsRecyclerView: RecyclerView
    private lateinit var adapter: ChartCurrenciesAdapter
    private lateinit var otherCurrency: ViewGroup

    private lateinit var dateTo: TextInputEditText
    private lateinit var dateFrom: TextInputEditText

    private var currentDatePicker: TextInputEditText? = null

    private lateinit var chosenCurrencyText: TextView
    private lateinit var seeAllCurrencies: TextView

    private lateinit var generateChartButton: MaterialButton

    private lateinit var noFavsAlert: ViewGroup

    private val viewModel: ChartViewModel by inject()

    private var favouriteCurrencies: MutableLiveData<List<CurrencyForFavs>> =
        MutableLiveData(listOf())

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chart, container, false)

        initViews(view)
        generateChart()
        initRecyclerViewFavCurrenciesForChart()
        initDatePickers()
        observeFavourites()
        observeViewModelData()
        getFavCurrencies()

        return view
    }

    private fun observeViewModelData() {
        viewModel.currency.observe(viewLifecycleOwner, {
            it?.let {
                chosenCurrencyText.setText("Chosen currency: " + it.currencyName)
            } ?: chosenCurrencyText.setText("Chosen currency: " + "NONE")
        })

        viewModel.dateFrom.observe(viewLifecycleOwner, {
            dateFrom.setText(it)
        })

        viewModel.dateTo.observe(viewLifecycleOwner, {
            dateTo.setText(it)
        })
    }

    private fun getFavCurrencies() {
        GlobalScope.launch {
            viewModel.getFavouriteCurrencies().collect { currencies ->

                val currenciesForFavs = currencies.map { currencyUniversal ->
                    CurrencyForFavs(
                        code = currencyUniversal.code,
                        currencyName = currencyUniversal.currencyName,
                        isFavourite = true,
                        isSelected = false
                    )
                }

                favouriteCurrencies.postValue(currenciesForFavs)
            }
        }
    }

    private fun observeFavourites() {
        favouriteCurrencies.observe(viewLifecycleOwner, { list ->
            if (list.isEmpty()) noFavsAlert.visibility = View.VISIBLE
            else View.GONE

            adapter.data = list
            adapter.notifyDataSetChanged()
        })
    }

    private fun initViews(view: View) {
        lineChart = view.findViewById(R.id.lineChart)
        favsRecyclerView = view.findViewById(R.id.favsRecyclerView)
        otherCurrency = view.findViewById(R.id.otherCurrency)

        dateTo = view.findViewById(R.id.dateToBody)
        dateFrom = view.findViewById(R.id.dateFromBody)

        chosenCurrencyText = view.findViewById(R.id.chosenCurrencyText)

        seeAllCurrencies = view.findViewById(R.id.seeAllCurrencies)
        seeAllCurrencies.setOnClickListener {
            SelectCurrencyDialogFragment(this@ChartFragment, CurrencySpinnerIndex.NONE)
                .show(parentFragmentManager, "SELECT_CURRENCY")
        }

        generateChartButton = view.findViewById(R.id.generateChartButton)
        generateChartButton.setOnClickListener {
            dateFrom.error = null
            dateTo.error = null
            getChartData()
        }

        noFavsAlert = view.findViewById(R.id.noFavsAlert)
    }

    @ExperimentalCoroutinesApi
    private fun getChartData() {
        GlobalScope.launch {
            viewModel.getChartData().collect { response ->
                when (response.flag) {
                    ResponseStatus.SUCCESS -> requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    }
                    ResponseStatus.FAILED -> {
                        if (response.error is ChartDataRequestValidatorResponse)
                            requireActivity().runOnUiThread {
                                setErrors(response.error as ChartDataRequestValidatorResponse)
                            }
                        else requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
    }

    private fun setErrors(response: ChartDataRequestValidatorResponse) {
        if (!response.isFirstDateValid) dateFrom.error = "Enter a valid date!"
        if (!response.isSecondDateValid) dateTo.error = "Enter a valid date!"
        if (!response.isCurrencyValid) chosenCurrencyText.let {
            it.text = "CHOOSE A CURRENCY"
            it.setTextColor(ColorStateList.valueOf(requireContext().resources.getColor(R.color.colorPrimaryDark)))
        }

        if (!response.isChronologyValid) chosenCurrencyText.let {
            it.text = "Something's wrong with the chronology here..."
            it.setTextColor(ColorStateList.valueOf(requireContext().resources.getColor(R.color.colorPrimaryDark)))
        }
    }

    private fun initDatePickers() {
        dateTo.setOnClickListener {
            currentDatePicker = dateTo
            showDialog()

        }

        dateFrom.setOnClickListener {
            currentDatePicker = dateFrom
            showDialog()
        }

        dateTo.doOnTextChanged { text, start, before, count ->
            viewModel.setDateTo(text.toString())
        }

        dateFrom.doOnTextChanged { text, start, before, count ->
            viewModel.setDateFrom(text.toString())
        }
    }


    private fun showDialog() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            activity!!,
            { _, year, month, day_of_month ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day_of_month
                val myFormat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                val date = sdf.format(calendar.time)
                //currentDatePicker!!.setText(date)
                when (currentDatePicker!!.id) {
                    R.id.dateToBody -> {
                        viewModel.setDateTo(date)
                    }
                    R.id.dateFromBody -> viewModel.setDateFrom(date)
                }
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        )

        val minTime = getLongAsDate(2002, 1, 2)

        dialog.datePicker.minDate = minTime

        calendar.add(Calendar.YEAR, 0)
        dialog.datePicker.maxDate = calendar.timeInMillis

        dialog.show()

    }

    private fun initRecyclerViewFavCurrenciesForChart() {
        adapter = ChartCurrenciesAdapter(favouriteCurrencies.value!!, requireContext())
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(favsRecyclerView)

        favsRecyclerView.adapter = adapter
        favsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun generateChart() {

        val entries = ArrayList<Entry>()

        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

        val vl = LineDataSet(entries, "My Type")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorAccent
        vl.fillAlpha = R.color.colorAccent

        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.data = LineData(vl)

        lineChart.axisRight.isEnabled = false

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.description.text = "Days"
        lineChart.setNoDataText("No data yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)

        val markerView = CustomMarker(requireContext(), R.layout.marker_view)
        lineChart.marker = markerView
    }

    companion object {
        fun getInstance(): ChartFragment = ChartFragment()
    }

    private fun getLongAsDate(year: Int, month: Int, date: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = date
        calendar[Calendar.MONTH] = month
        calendar[Calendar.YEAR] = year
        return calendar.timeInMillis
    }

    override fun setCurrency(currency: CurrencyForView, spinnerIndex: CurrencySpinnerIndex) {
        if (currency.isFavourite) {
            otherCurrency.visibility = View.GONE
        } else {
            otherCurrency.visibility = View.VISIBLE
        }
    }

}