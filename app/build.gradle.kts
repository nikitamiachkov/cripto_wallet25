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

}
