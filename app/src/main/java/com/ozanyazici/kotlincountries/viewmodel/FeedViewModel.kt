package com.ozanyazici.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozanyazici.kotlincountries.model.Country
import com.ozanyazici.kotlincountries.service.CountryAPIService
import com.ozanyazici.kotlincountries.service.CountryDatabase
import com.ozanyazici.kotlincountries.util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application) {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private val countryApiService = CountryAPIService()
    private  val disposable =  CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    //Zamanı nanosaniye olarak almıştık.
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    //Veriyi Apiden çekeli 10 dk dan fazla olduğunda veriyi tekrar apiden çekiyoruz. Olmadıysa sqLitedan çekiyoruz.
    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    //Swipe yapıldığında veriyi sadece apiden çekmek için bu metodu yazdık.
    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From Sqlite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        //Verileri önce rooma kaydedip sonra görüntülüyoruz. İnternetten tasarruf sağlamak için.
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    //Room save
    private fun storeInSQLite(list: List<Country>) {
        launch {
            //invoke operatörü burada kendini gösteriyor. ConutryDatabase derken aslında invoke a contexti gönderiyoruzki instance oluştursun.
            val dao = CountryDatabase(getApplication()).countryDao()
            //Önce eskiden kalan verileri temizliyoruz.
            dao.deleteAllCountries()
            //list.toTypedArray() ifadesi, bir koleksiyonu (örneğin, bir liste) bir diziye dönüştürür.
            //* (spread operatörü) ifadesi, bir diziyi vararg olarak kullanılabilir hale getirir.
            val listLong = dao.insertAll(*list.toTypedArray())
            //db ye kaydedilen ülkelerin otomatik oluşturulan idlerini ülkelerin uuid lerine atıyorum.
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)
        }

        //zamanı en hassas şekilde almak için nanotime kullandım. Bu kodu burada yazarak
        //verilerin db ye kaydedildiği andaki zamanı parametre olarak alıp kaydetmiş olacağım.
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}