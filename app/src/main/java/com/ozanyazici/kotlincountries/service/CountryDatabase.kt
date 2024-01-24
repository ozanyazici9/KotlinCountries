package com.ozanyazici.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ozanyazici.kotlincountries.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun countryDao(): CountryDao

    //Singleton
    //Veritabanı gibi kaynaklar, aynı anda birden fazla iş parçacığı tarafından erişilebilir ve eğer uygun önlemler alınmazsa,
    //bu eşzamanlı erişimler çakışmalara (race conditions) ve tutarsızlık sorunlarına neden olabilir. Bu yüzden singleton ı kullanırız.

    //companion (eşlikçi, yardımcı) object, Kotlin'de bir sınıfın içindeki özel bir nesnedir ve bu nesne, sınıfın bir örneğine ait olmayan,
    //sınıf düzeyindeki öğeleri (üyeleri) temsil eder. Bu nesne, bir sınıfın içindeki bir tür "static" blok gibidir.
    //Böylelikle diğer scopelardan erişebilicem. ve genellikle sınıf düzeyindeki özellikleri veya fonksiyonları gruplamak
    //ve sınıfın örneğine ait olmayan özel işlevler veya durumları ifade etmek için kullanılır.

    companion object {

        //Volatile (uçucu): volatile keyword'ü, değişkenin değerinin her değiştiğinde diğer thread'lerin bu değişikliği görmesini sağlar,
        //bu da güvenli ve doğru bir paylaşım sağlar.
        //Eğer farklı threadler kullanmasaydım ihtiyacım olmayacaktı.
        @Volatile private var instance: CountryDatabase? = null

        //synchronnized ın kilitlenip kilitlenmeyeceğini kontrol eden değişken.
        private val lock = Any()

        //invoke (çağırmak): anahtar kelimesi, bir sınıfın bir nesnesini fonksiyon gibi çağırmak için kullanılır.
        //burada ise invoke operatörü, sınıfın nesnesini bir fonksiyon gibi çağırmanın kısa bir yolunu sağlar.
        //Bu, sınıfın kurucu fonksiyonunu çağırmakla aynı işlevi görür.
        //invoke operatörü, bu sınıfın bir örneğini oluşturmak için kullanılır.
        //instance'ın değeri null ise veya başka bir thread tarafından değiştirilirse,
        //synchronized bloğu içine girilir. Bu blok, aynı anda sadece bir thread'in bu kod bloğuna girmesini sağlar.
        //makeDatabase fonksiyonu ile Room veritabanını oluşturulur ve bu örnek atama işlemi yapılır.

        //operator kelimesi, bu fonksiyonun bir operatörü aşırı yüklediğini belirtir.
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        //Veritabanını oluşturur.
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            //Fragmentların contextini kullanmamamın sebebi onlar kapandığında db ye erişimim kesilmesin uygulamam çökmesin diye.
            context.applicationContext, CountryDatabase::class.java, "countrydatabase"
        ).build()
    }
}