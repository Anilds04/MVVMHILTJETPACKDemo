import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mvvmhiltjetpackdemo"
    compileSdk = 36



    defaultConfig {
        applicationId = "com.example.mvvmhiltjetpackdemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //Added to read google client id
        val webClientId: String = gradleLocalProperties(rootDir, providers).getProperty("WEB_CLIENT_ID")
        buildConfigField("String", "WEB_CLIENT_ID", "\"$webClientId\"")
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
        buildConfig = true
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
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
   // implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.github.skydoves:landscapist-glide:2.2.12")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation (platform("androidx.compose:compose-bom:2025.01.00"))
    implementation ("androidx.compose.material:material-icons-extended")

    //Google signIn
    implementation("androidx.credentials:credentials:1.6.0-alpha05")
    implementation("androidx.credentials:credentials-play-services-auth:1.6.0-alpha05")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Firebase BoM
    implementation (platform("com.google.firebase:firebase-bom:33.1.2"))

    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth")
    //Data store
    implementation("androidx.datastore:datastore-preferences:1.1.0")

}