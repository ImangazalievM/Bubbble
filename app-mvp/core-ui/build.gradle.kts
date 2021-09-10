plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.Core.di))
    api(project(Modules.Core.core))

    api(Dependencies.supportAppCompat)
    api(Dependencies.supportDesign)
    api(Dependencies.supportCardView)
    api(Dependencies.customTabs)

    api(Dependencies.glide)
    api(Dependencies.photoView)

    api(Dependencies.moxy)
    
    api(Dependencies.coroutinesCore)
    api(Dependencies.coroutinesAndroid)

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)

    //permissions request
    api("com.afollestad.assent:core:3.0.0-RC4")
    api("com.afollestad.assent:rationales:3.0.0-RC4")
}