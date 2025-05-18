plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.masterapp.myownapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myownapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"  // ✅ Correct target version
    }

    buildFeatures {
        buildConfig = true  // ✅ Prevents deprecation warning
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.mlkit:translate:17.0.1")
    implementation("com.google.mlkit:translate:17.0.2")
}
