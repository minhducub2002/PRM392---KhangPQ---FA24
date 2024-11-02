@file:Suppress("UNUSED_EXPRESSION")

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("my_config") {
            storeFile = file("D:\\FPT\\Term_9\\PRM392\\KeyStore.jks")
            storePassword = "03082002"
            keyAlias = "ducnm"
            keyPassword = "03082002"
        }
    }
    namespace = "com.ducnm.tramsacxedien"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ducnm.tramsacxedien"
        minSdk = 26
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
            signingConfig = signingConfigs.getByName("my_config")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // import thu vien room database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
}