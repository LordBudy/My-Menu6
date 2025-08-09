plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("kotlin-parcelize")

}
apply(plugin = "androidx.navigation.safeargs.kotlin")
android {
    namespace = "com.example.mymenu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mymenu"
        minSdk = 24

        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.appcompat)
    implementation(libs.car.ui.lib)

    kapt(libs.androidx.room.compiler)
//coroutines
    implementation(libs.kotlinx.coroutines.android)
//picasso
    implementation(libs.picasso)
//lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.common)
//navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
// Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    //implementation(libs.koin.androidx.compose)
  //  implementation(libs.koin.androidx.viewmodel)

    implementation(libs.androidx.lifecycle.livedata.ktx) {
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime-compose")
    }
    implementation(libs.androidx.lifecycle.viewmodel.ktx) {
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime-compose")
    }
    implementation(libs.androidx.lifecycle.common) {
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime-compose")
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}