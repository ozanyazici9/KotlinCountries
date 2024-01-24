package com.ozanyazici.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ozanyazici.kotlincountries.model.Country

@Dao
interface CountryDao {

    //Data Accsess Object

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    //Insert -> INSERT INTO
    //suspend -> metodlar durdurlup, yeterli kaynak olduğunda tekrar başlatılabilen metodlardır.
    //Sadece suspend metodlar içinde ve coroutineler içinde kullanılabilir.
    //vararg -> Kotlin'de, bir fonksiyona değişken sayıda argüman geçmenizi sağlayan bir özelliktir.
    //List<Long> -> primary keyleri döndürecek.

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}