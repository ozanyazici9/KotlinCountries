package com.ozanyazici.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    //Sharedpreferencesdan normaldede tek bir obje oluşturulur.
    //Farklı theradlerde çalıştığımız için bunu singleton olarak oluşturacağız countryDatabasede olduğu gibi.
    //Util paketinin içinde olmasının sebebi sınıflar arasında ortak olarak kullanılacak olmasındandır.

    //Api den veriyi çekeli ne kadar oldu onu tutmak için shradpreferences oluşturuyoruz. Zamana göre veriyi roomdan veya apiden çekeceğiz.
    companion object {

        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance  = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time: Long) {
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    //katdedilen zamanı almak için.
    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)
}