package com.gabez.yet_another_currency_converter.chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.chart.chartGenerator.CustomMarker
import com.gabez.yet_another_currency_converter.chart.topListView.ChartCurrenciesAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ChartFragment : Fragment() {


    private lateinit var lineChart: LineChart
    private lateinit var favsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chart, container, false)

        lineChart = view.findViewById(R.id.lineChart)
        favsRecyclerView = view.findViewById(R.id.favsRecyclerView)

        generateChart()
        initRecyclerViewFavCurrenciesForChart()

        return view
    }

    private fun initRecyclerViewFavCurrenciesForChart(){
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(favsRecyclerView)

        val tempList = arrayListOf<String>("PLN", "USD", "ABC", "CHRK", "CKR", "CDA", "ASD", "UWU", "ITP")

        favsRecyclerView.adapter = ChartCurrenciesAdapter(tempList, requireContext())
        favsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    fun generateChart(){

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
        lineChart.setNoDataText("No forex yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)

        val markerView = CustomMarker(requireContext(), R.layout.marker_view)
        lineChart.marker = markerView
    }

    companion object{
        fun getInstance(): ChartFragment = ChartFragment()
    }

}