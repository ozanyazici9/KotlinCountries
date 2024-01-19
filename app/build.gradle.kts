plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.ozanyazici.kotlincountries"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ozanyazici.kotlincountries"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    val navVersion = "2.7.6"
    val lifeCycleExtensionVersion = "2.2.0"
    val supportVersion = "28.0.0"
    val retrofitVersion = "2.9.0"
    val glideVersion = "4.9.0"
    val rxJavaVersion = "3.1.5"
    val roomVersion = "2.6.1"
    val preferencesVersion = "1.2.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Bu kütüphane, özellikle Android uygulamalarındaki aktiviteler ve fragmentler arasındaki yaşam döngüsü olaylarına uyum sağlamada kullanılır.
    implementation ("androidx.lifecycle:lifecycle-extensions:$lifeCycleExtensionVersion")

    //Room
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    //noinspection KaptUsageInsteadOfKsp
    kapt ("androidx.room:room-compiler:$roomVersion")//@Dao gibi anotasyonları işlemek için kullanılıyor
    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    //Dizayn için
    implementation ("com.google.android.material:material:1.11.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion")

    //RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")

    //İnternetten resim indirmek için
    implementation ("com.github.bumptech.glide:glide:$glideVersion")

    //resimdeki renkleri analiz edip ona göre arkaplan renklerimizi ayarlamamızı sağlayacak.
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation ("com.google.android.material:material:1.11.0")

    //SharedPreferences
    implementation ("androidx.preference:preference-ktx:$preferencesVersion")
}