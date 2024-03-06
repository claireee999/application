package com.example.application.model

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class Model {
    data class ModelCountry(val name: String, val region: String, val code: String, val capital: String): BaseObservable(){}

    var countries = ObservableArrayList<ModelCountry>()

    private fun parseCountryData(countryString: String): ArrayList<ModelCountry> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listCountry = Types.newParameterizedType(List::class.java, ModelCountry::class.java)
        val countryAdapter: JsonAdapter<ArrayList<ModelCountry>> = moshi.adapter<ArrayList<ModelCountry>>(listCountry)
        return countryAdapter.fromJson(countryString)!!
    }

    fun setCountries(countryData: String) {
        countries.addAll(parseCountryData(countryData))
    }
}


