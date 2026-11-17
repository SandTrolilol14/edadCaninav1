plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "es.davante.edadcanina"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "es.davante.edadcanina.mvc"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Activamos DataBinding para que Android genere clases que enlacen
    // el XML con objetos Java y así no tener que usar findViewById
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}