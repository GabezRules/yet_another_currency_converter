package com.gabez.yet_another_currency_converter.chart.app

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.chart.app.chartGenerator.CustomMarker
import com.gabez.yet_another_currency_converter.chart.app.chartValidator.ChartDataRequestValidatorResponse
import com.gabez.yet_another_currency_converter.chart.app.topListView.ChartCurrenciesAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment : Fragment(), KoinComponent {

    private lateinit var lineChart: LineChart
    private lateinit var favsRecyclerView: RecyclerView

    private lateinit var dateTo: TextInputEditText
    private lateinit var dateFrom: TextInputEditText

    private var currentDatePicker: TextInputEditText? = null

    private lateinit var chosenCurrencyText: TextView

    private lateinit var generateChartButton: MaterialButton

    private val viewModel: ChartViewModel by inject()

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

        return view
    }

    private fun initViews(view: View) {
        lineChart = view.findViewById(R.id.lineChart)
        favsRecyclerView = view.findViewById(R.id.favsRecyclerView)

        dateTo = view.findViewById(R.id.dateToBody)
        dateFrom = view.findViewById(R.id.dateFromBody)

        chosenCurrencyText = view.findViewById(R.id.chosenCurrencyText)

        generateChartButton = view.findViewById(R.id.generateChartButton)
        generateChartButton.setOnClickListener {
            dateFrom.error = null
            dateTo.error = null
            val getChartDataResponse = viewModel.getChartData()
            if(!getChartDataResponse.isValid) setErrors(getChartDataResponse)
        }
    }

    private fun setErrors(response: ChartDataRequestValidatorResponse){
        if(!response.isFirstDateValid) dateFrom.error = "Enter a valid date!"
        if(!response.isSecondDateValid) dateTo.error = "Enter a valid date!"
        if(!response.isCurrencyValid) chosenCurrencyText.text = "SELECT CURRENCY"
    }

    private fun initDatePickers(){
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
                currentDatePicker!!.setText(date)
                when(currentDatePicker!!.id){
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
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(favsRecyclerView)

        val tempList =
            arrayListOf<String>("PLN", "USD", "ABC", "CHRK", "CKR", "CDA", "ASD", "UWU", "ITP")

        favsRecyclerView.adapter = ChartCurrenciesAdapter(tempList, requireContext())
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

}