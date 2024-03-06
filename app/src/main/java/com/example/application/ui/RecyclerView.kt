package com.example.application.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.viewModel.CountryViewModel

class CountryAdapter (private val countries: List<CountryViewModel.VMCountry>): RecyclerView.Adapter<CountryAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryNameTextView = itemView.findViewById<TextView>(R.id.country_name)
        val countryCodeTextView = itemView.findViewById<TextView>(R.id.country_code)
        val countryCapitalTextView = itemView.findViewById<TextView>(R.id.country_capital)
        val countryRegionTextView = itemView.findViewById<TextView>(R.id.country_region)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_country, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val country: CountryViewModel.VMCountry = countries[position]
        viewHolder.countryNameTextView.text = country.name
        viewHolder.countryRegionTextView.text = country.region
        viewHolder.countryCapitalTextView.text = country.capital
        viewHolder.countryCodeTextView.text = country.code
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}