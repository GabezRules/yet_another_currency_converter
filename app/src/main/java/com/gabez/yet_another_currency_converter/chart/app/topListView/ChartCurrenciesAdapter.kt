package com.gabez.yet_another_currency_converter.chart.app.topListView

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabez.yet_another_currency_converter.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChartCurrenciesAdapter(private var data: List<String>, private var context: Context) :
    RecyclerView.Adapter<ChartCurrenciesAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var currencyText: TextView = view.findViewById(R.id.currencyText)
        var background: FloatingActionButton = view.findViewById(R.id.currencyView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_in_fragment_chart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.currencyText.text = data[position]
        if(data[position] == "PLN") holder.background.backgroundTintList = ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimaryDark))

    }

    override fun getItemCount(): Int = data.size
}