package com.ozanyazici.kotlincountries.service

import com.ozanyazici.kotlincountries.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>//Single veriyi bir kere çeker sürekli veriyi çekmeye gerek olmadığı için bu projede single ı kullanıyoruz
}