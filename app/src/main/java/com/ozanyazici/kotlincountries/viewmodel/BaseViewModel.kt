package com.ozanyazici.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//Birden fazla yerde coroutineleri kullanacağım için coroutinler için ayrı bir sınıf açtım.
//Bu sınıfı kullanacağım yerlede viewModelı da ayrıca kalıtım almak yerine ViewModel sınıfınıda buradaki sınıfta kalıtıcam.
//Bulunulan fragmentın kapanması durumunda burası diğer sınıflar içinde çalışmaya devam etmesi için ViewModelı değil
//AndroidViewModel sınıfını kalıtacağım o da benden application parametresi isteyecek.
//Böylelikle bir fragmentın bu sınıfla işi bitip kapandığında diğer fragmentlar/sınıflar bu sınıfı kullanıp çalışmaya devam edebilecek.
//Bu sınıfı sadece implementa edeceğimiz içinde sınıfı abstract tanımlıyoruz.
abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    //Önce işini yap sonra main therade geri dön diyoruz burada
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    //Diyelimki uygulama kapatıldı o zaman bu metod çalışır. işi iptal etmek için onCleared metodunda job.cancel diyoruz.
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}