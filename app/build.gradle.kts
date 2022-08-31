plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.sandbox"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.sandbox"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

//    buildTypes {
//        release {
//            minifyEnabled false
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta01"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:1.2.0-beta01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-beta01")
    implementation("androidx.compose.material:material:1.2.0-beta01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-beta01")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-beta01")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0-beta01")
}

object Version {
    const val HILT = "2.43"
//    const val HILT = "2.38.1"

    const val ROOM = "2.5.0-alpha02"
//    const val ROOM = "2.4.2"

    const val LIFECYCLE = "2.6.0-alpha01"

    //    const val LIFECYCLE = "2.5.0"
    const val ARCH = "2.1.0"

    const val APPCOMPAT = "1.5.0-beta01"

    object Compose {
        const val ANIMATION = "1.2.1"
        const val COMPILER = "1.3.0"
        const val FOUNDATION = "1.2.1"
        const val MATERIAL = "1.2.1"
        const val MATERIAL3 = "1.0.0-alpha16"
        const val RUNTIME = "1.2.1"
        const val UI = "1.2.1"
        const val CONSTRAINT_LAYOUT = "1.0.1"
//        const val CONSTRAINT_LAYOUT = "1.1.0-alpha01"

        const val ACTIVITY = "1.5.1"
        const val VIEWMODEL = "2.5.1"
        const val PAGING = "1.0.0-alpha16"
    }
}