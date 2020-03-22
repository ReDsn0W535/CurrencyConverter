package com.example.currencyconverter.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R

class CurrencyRecyclerAdapter(
    private val currencies: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.bind(currency)
    }


    inner class CurrencyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currency: String) {
            itemView.findViewById<TextView>(R.id.currency_name).apply {
                text = currency
                setOnClickListener {
                    onClick.invoke(currency)
                }
            }
        }

    }
}