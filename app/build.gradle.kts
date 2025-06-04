plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.kaban2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kaban2"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Supabase
    //Supabase
    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.auth.kt)
    implementation(libs.realtime.kt)
    implementation (libs.storage.kt)

    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.0.0")



    //сказали
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.google.android.material:material:1.11.0")

    //Ktor
    implementation(libs.ktor.client.android)

    //Navigation
    implementation (libs.androidx.navigation.compose)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)


    //Coil
    implementation(libs.coil.compose)

    //LiveData Дата
    implementation (libs.androidx.runtime.livedata)

    //тесты
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")
    //implementation(libs.gotrue.kt)
    //implementation(libs.core)

    /*/ JUnit (4.13.2)
    testImplementation("junit:junit:4.13.2")

// Kotlin Coroutines Test (1.7.3)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

// Mockito Core (5.2.0)
    testImplementation("org.mockito:mockito-core:5.2.0")

// Mockito Kotlin (4.1.0)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

// Turbine for Flow testing (1.0.0)
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation(kotlin("test"))


    // To use the androidx.test.core APIs
    androidTestImplementation("androidx.test:core:1.6.1")
    // Kotlin extensions for androidx.test.core
    androidTestImplementation("androidx.test:core-ktx:1.6.1")

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")

    // To use the Truth Extension APIs
    androidTestImplementation("androidx.test.ext:truth:1.6.0")

    // To use the androidx.test.runner APIs
    androidTestImplementation("androidx.test:runner:1.6.2")

    // To use android test orchestrator
    androidTestUtil("androidx.test:orchestrator:1.5.1")

    testImplementation("org.slf4j:slf4j-simple:2.0.7")

    // Для тестирования LiveData
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Или если используете AndroidX Test
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

    // JUnit (4.13.2)
    testImplementation("junit:junit:4.13.2")

// Kotlin Coroutines Test (1.7.3)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

// Mockito Core (5.2.0)
    testImplementation("org.mockito:mockito-core:5.2.0")

// Mockito Kotlin (4.1.0)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

// Turbine for Flow testing (1.0.0)
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation(kotlin("test"))

    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("io.mockk:mockk:1.13.7")

    // Coroutines testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Core testing library for Android components
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // JUnit Runner for Android instrumentation tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Unit testing runner
    testImplementation("junit:junit:4.13.2")

    // Supabase SDK dependencies

    // If using Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson dependency
    implementation("com.google.code.gson:gson:2.10.1")

    // Coroutines support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Jetpack Architecture Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Additional Jetpack Modules
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Google Play Services Authentication
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Performance Optimization
    debugImplementation("androidx.profileinstaller:profileinstaller:1.3.1")

    // Optional: Firebase Crashlytics
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("androidx.lifecycle:lifecycle-runtime:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    // Инструменты для Espresso (опционально)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

*/

    // JUnit (4.13.2)
    testImplementation("junit:junit:4.13.2")

// Kotlin Coroutines Test (1.7.3)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

// Mockito Core (5.2.0)
    testImplementation("org.mockito:mockito-core:5.2.0")

// Mockito Kotlin (4.1.0)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

// Turbine for Flow testing (1.0.0)
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation(kotlin("test"))


    // To use the androidx.test.core APIs
    androidTestImplementation("androidx.test:core:1.6.1")
    // Kotlin extensions for androidx.test.core
    androidTestImplementation("androidx.test:core-ktx:1.6.1")

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")

    // To use the Truth Extension APIs
    androidTestImplementation("androidx.test.ext:truth:1.6.0")

    // To use the androidx.test.runner APIs
    androidTestImplementation("androidx.test:runner:1.6.2")

    // To use android test orchestrator
    androidTestUtil("androidx.test:orchestrator:1.5.1")

    testImplementation("org.slf4j:slf4j-simple:2.0.7")

    // Для тестирования LiveData
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Или если используете AndroidX Test
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")



    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")



    /*var compose_version = "1.8.2" // или ваша текущая версия
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_version")

    // Навигация (версия должна совпадать с версией Navigation Component)
    var nav_version = "2.7.7" // актуальная версия Navigation
    androidTestImplementation ("androidx.navigation:navigation-testing:$nav_version")

    // AndroidX Test
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")

    // Core KTX и Runtime Compose для TestNavHostController
    androidTestImplementation ("androidx.core:core-ktx:1.16.0")
    androidTestImplementation ("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")


    androidTestImplementation ("androidx.activity:activity-compose:1.8.0")

    // Compose Testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.0")

    // Navigation Testing
    androidTestImplementation ("androidx.navigation:navigation-testing:2.7.5")

    // AndroidX Test
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")


    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")

    // Compose тестирование
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.0")

    // Навигация (если тестируете навигацию)
    androidTestImplementation ("androidx.navigation:navigation-testing:2.7.5")*/

}