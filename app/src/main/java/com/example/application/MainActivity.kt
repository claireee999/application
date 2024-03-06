package com.example.application

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.ui.CountryAdapter
import com.example.application.viewModel.CountryViewModel

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model: CountryViewModel by viewModels { CountryViewModel.Factory }
        val countries: LiveData<List<CountryViewModel.VMCountry>> = model.getCountries()

        val rvCountry = findViewById<View>(R.id.country_view) as RecyclerView
        val adapter = CountryAdapter(countries.value!!)
        rvCountry.adapter = adapter
        rvCountry.layoutManager = LinearLayoutManager(this)
        countries.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }
}