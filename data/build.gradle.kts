plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.hvasoft.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", (project.properties["apiKey"] ?: "").toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(project(":domain"))

    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Networking
    api(libs.bundles.networking.impl)

    // Database
    implementation(libs.bundles.database.impl)
    kapt(libs.room.compiler)

    // Paging3
    implementation(libs.bundles.paging.impl)
    implementation(libs.paging.runtime)
    implementation(libs.room.paging)
    testImplementation(libs.paging.common)

    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}