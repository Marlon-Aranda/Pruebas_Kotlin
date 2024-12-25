plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.pruebas_01"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pruebas_01"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"  // Define la versión predeterminada

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions("version")
//    productFlavors {
//        version_1_0 {
//            dimension = "version"
//            buildConfigField "String", "VERSION_NAME", "\"1.0\""
//        }
//        version_2_0 {
//            dimension = "version"
//            buildConfigField "String", "VERSION_NAME", "\"2.0\""
//        }
//    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configuración correcta de los manifiestos según cada sabor
//    sourceSets {
//        version_1_0 {
//            manifest.srcFile 'src/version_1_0/AndroidManifest.xml'
//        }
//        version_2_0 {
//            manifest.srcFile 'src/version_2_0/AndroidManifest.xml'
//        }
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}