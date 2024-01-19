package com.ozanyazici.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozanyazici.kotlincountries.R

//Util" (utility)(yardımcı program) paketi, genellikle Android projelerinde genel amaçlı yardımcı sınıfları ve işlevleri içeren bir paket olarak açılır.
//Bu paket, projenin farklı kısımlarında kullanılacak ortak fonksiyonları gruplamak ve düzenlemek için kullanılır.
//Bu yardımcı sınıflar ve fonksiyonlar genellikle proje genelinde yaygın olarak kullanılan işlemleri gerçekleştirmek için tasarlanır.


//Extension : Kotlin ktx(kotlin extensions) mevcut kotlin kütüphanelerine kendimiz eklentiler yazabiliyoruz.
//Aşağıda String sınıfına extension olarak myExtension adlı bir metod ekliyorum.

/*
fun String.myExtension(myParameter: String) {
    println(myParameter)
}
 */

//ImageView a extension ekledik ve glide ile resmi indirdik
fun ImageView.downloadFromURL(url: String?, progressDrawable: CircularProgressDrawable) {
    //glide ın default değerlerini girdik
    val options = RequestOptions()
        .placeholder(progressDrawable)//resim yüklenirken yaptığımız progressbarın gözükmesini sağladık.
        .error(R.mipmap.ic_launcher)//indirmede hata olduğunda default olarak hangi resim gösterilecek onu ayarladık

    Glide.with(context)
        .setDefaultRequestOptions(options)//yukarıdaki optionlar
        .load(url)//indirelek resmin urlsi
        .into(this) //resmin neyin içinde gösterileceği
}

//gösterilecek olan progressbar
fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f //kalınlığı
        centerRadius = 40f // çapı
        start()
    }
}