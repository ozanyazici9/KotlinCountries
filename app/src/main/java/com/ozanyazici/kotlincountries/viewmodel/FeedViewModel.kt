package com.ozanyazici.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozanyazici.kotlincountries.model.Country

class FeedViewModel: ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {

        val country = Country("Turkiye","Asia","Ankara","TRY","Turkish","www.ss.com")
        val country2 = Country("Germany","Europa","Berlin","EU","German","www.ss.com")
        val country3 = Country("France","Europa","Paris","EU","French","www.ss.com")

        val countryList = arrayListOf<Country>(country,country2,country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }
}