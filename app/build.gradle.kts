import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.sandbox"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.sandbox"
        minSdk = 21
        targetSdk = 33
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
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
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

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    addRoomApi()
    addRoomCompiler()
    implementation("androidx.core:core-ktx:1.8.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE}")
    implementation("androidx.activity:activity-compose:${Version.Compose.ACTIVITY}")
    implementation("androidx.compose.ui:ui:${Version.Compose.UI}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Version.Compose.UI}")
    implementation("androidx.compose.material:material:${Version.Compose.MATERIAL}")
    implementation("androidx.paging:paging-compose:${Version.Compose.PAGING}")
    implementation("androidx.compose.material3:material3:${Version.Compose.MATERIAL3}")
    implementation("androidx.compose.material3:material3-window-size-class:${Version.Compose.MATERIAL3}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Version.Compose.VIEWMODEL}")

    debugImplementation("androidx.compose.ui:ui-tooling:${Version.Compose.UI}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Version.Compose.UI}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-beta01")
}

fun DependencyHandler.addRoomApi(transient: Boolean = false) {
    add(apiOrImpl(transient), "androidx.room:room-runtime:${Version.ROOM}")
    add(apiOrImpl(transient), "androidx.room:room-ktx:${Version.ROOM}")
    add(apiOrImpl(transient), "androidx.room:room-paging:${Version.ROOM}")
    add(apiOrImpl(transient), "androidx.room:room-guava:${Version.ROOM}")
}

fun DependencyHandler.addRoomCompiler() {
    add("kapt", "androidx.room:room-compiler:${Version.ROOM}")
}

fun apiOrImpl(transient: Boolean) = if (transient) "api" else "implementation"

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