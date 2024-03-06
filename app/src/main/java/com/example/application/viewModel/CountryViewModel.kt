package com.example.application.viewModel

import android.util.Log
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.application.model.Model
import com.example.application.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository): ViewModel() {
    data class VMCountry(val name: String, val region: String, val code: String, val capital: String) {}

    private val model = Model()
    private val countries = mutableListOf<VMCountry>()
    private val countriesLiveData = MutableLiveData<MutableList<VMCountry>>(countries)

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel>
                    create(modelClass: Class<T>, extras: CreationExtras): T {
                val countryRepository = CountryRepository()
                return CountryViewModel(countryRepository) as T
            }
        }
    }

    private fun getCountryData() {
        viewModelScope.launch {
            val result = countryRepository.getCountryData()
            model.setCountries(result)
        }
    }

    init {
        Log.i("init", model.countries.toString())
        model.countries.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Model.ModelCountry>>() {
            override fun onChanged(sender: ObservableList<Model.ModelCountry>?) {
            }
            override fun onItemRangeChanged(sender: ObservableList<Model.ModelCountry>?, positionStart: Int, itemCount: Int) { }
            override fun onItemRangeInserted(sender: ObservableList<Model.ModelCountry>?, positionStart: Int, itemCount: Int) {
                countriesLiveData.value?.clear()
                countries.clear()
                sender?.forEach {
                    val country = VMCountry(it.name, it.region, it.code, it.capital)
                    countries.add(country)
                }
                countriesLiveData.value = countries
            }
            override fun onItemRangeMoved(sender: ObservableList<Model.ModelCountry>?, fromPosition: Int, toPosition: Int, itemCount: Int) {}
            override fun onItemRangeRemoved(sender: ObservableList<Model.ModelCountry>?, positionStart: Int, itemCount: Int) {}
        })
        getCountryData()
    }

    fun getCountries(): LiveData<List<VMCountry>> {
        return countriesLiveData as LiveData<List<VMCountry>>
    }
}