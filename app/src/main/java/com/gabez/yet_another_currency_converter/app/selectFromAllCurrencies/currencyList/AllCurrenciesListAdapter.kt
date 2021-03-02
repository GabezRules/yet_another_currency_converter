package com.gabez.yet_another_currency_converter.app.selectFromAllCurrencies.currencyList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.entities.CurrencyForView

class AllCurrenciesListAdapter(private val callback: CurrencyListCallback) :
    RecyclerView.Adapter<AllCurrenciesListAdapter.ViewHolder>() {

    private val data: ArrayList<CurrencyForView> = ArrayList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var currencyShortName: TextView = view.findViewById(R.id.currencyShortName)
        var currencyLongName: TextView = view.findViewById(R.id.currencyLongName)
        var isFav: ImageView = view.findViewById(R.id.isFavouriteImage)
        var container: View = view.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = data[position]
        holder.currencyShortName.text = currency.code
        holder.currencyLongName.text = currency.currencyName

        if (currency.isFavourite) {
            holder.isFav.setImageResource(R.drawable.ic_star_full)
            holder.isFav.setOnClickListener { callback.unmarkCurrency(currency) }
        } else {
            holder.isFav.setImageResource(R.drawable.ic_star_empty)
            holder.isFav.setOnClickListener { callback.markCurrency(currency) }
        }

        holder.container.setOnClickListener {
            callback.setCurrency(currency)
        }

    }

    override fun getItemCount(): Int = data.size

    fun setData(nData: List<CurrencyForView>){
        data.clear()
        data.addAll(nData)
        this@AllCurrenciesListAdapter.notifyDataSetChanged()
    }
}