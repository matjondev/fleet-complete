plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 31
    buildToolsVersion = "29.0.3"

    defaultConfig {
        minSdk = 26
        targetSdk = 31
        multiDexEnabled = true
        applicationId = "com.fleetcomplete.android"
        versionCode = 1
        versionName = "0.0.1"
        setProperty("archivesBaseName", "build-Ver-$versionName")
    }
    /*signingConfigs {
        create("key") {
            storeFile = file("../config/fleetcomplete")
            storePassword = "CoLenTran"
            keyAlias = "key0"
            keyPassword = "CoLenTran"
        }
    }*/
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "API_URL", "\"https://app.ecofleet.com/seeme/Api/\"")
            buildConfigField("String", "API_KEY", "\"home.assignment.2-1230927\"")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
//            signingConfig = signingConfigs.findByName("key")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("stage") {
            buildConfigField("String", "API_URL", "\"https://app.ecofleet.com/seeme/Api/\"")
            buildConfigField("String", "API_KEY", "\"home.assignment.2-1230927\"")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
//            signingConfig = signingConfigs.findByName("key")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        }
        getByName("release") {
            buildConfigField("String", "API_URL", "\"https://app.ecofleet.com/seeme/Api/\"")
            buildConfigField("String", "API_KEY", "\"home.assignment.2-1230927\"")
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
//            signingConfig = signingConfigs.findByName("key")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    /**
     * Core
     */
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.30")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    val KOTLIN_COROUTINES_VERSION = "1.5.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLIN_COROUTINES_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLIN_COROUTINES_VERSION")

    //  Lifecycle - used for ViewModel LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //noinspection LifecycleAnnotationProcessorWithJava8
    kapt("androidx.lifecycle:lifecycle-compiler:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")


    /*
     * REST API: retrofit
     */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.readystatesoftware.chuck:library:1.1.0")

    // Firebase
    implementation("com.google.firebase:firebase-core:20.0.1")
    implementation("com.google.firebase:firebase-analytics:20.0.1")
    implementation("com.google.firebase:firebase-crashlytics:18.2.6")

    // Koin for Android dependency injection
    implementation("org.koin:koin-android-viewmodel:2.2.2")

    // Coil
    implementation("io.coil-kt:coil:1.2.2")
    implementation("io.coil-kt:coil-svg:1.2.0")
    implementation("io.coil-kt:coil-video:1.4.0")

    // Circle Image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //  Multidex
    implementation("androidx.multidex:multidex:2.0.1")

    //  Logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Auto Update
    implementation("com.google.android.play:core:1.10.2")

    // Maps
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.google.android.libraries.places:places:2.5.0")

    // RxAndroid Permissions
    implementation("com.markodevcic:peko:2.1.4")

    // Loading
    implementation("com.airbnb.android:lottie:4.2.0")

    //Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")
}
