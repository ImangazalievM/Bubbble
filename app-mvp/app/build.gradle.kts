plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "com.bubbble"
        minSdkVersion(21)
        targetSdkVersion(30)

        versionCode = 3
        versionName = "1.1.0"

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    
    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    signingConfigs {
        create("bubbble") {
            storeFile(file("keystore.jks"))
            storePassword = "bubbble"
            keyAlias = "bubbble"
            keyPassword = "bubbble"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("bubbble")
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("bubbble")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("test").java.srcDirs("src/unitTests/java")
        getByName("test").java.srcDirs("src/integrationTests/java")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Core.core))
    implementation(project(Modules.Core.data))
    implementation(project(Modules.AppMvp.coreUi))
    implementation(project(Modules.AppMvp.featureShots))
    implementation(project(Modules.AppMvp.featureShotDetails))

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)

    kapt(Dependencies.moxyCompiler)

    // Developer Tools
    debugImplementation("com.facebook.stetho:stetho:1.5.1")
    debugImplementation("com.facebook.stetho:stetho-okhttp3:1.5.1")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    debugImplementation("com.github.mrmike:ok2curl:0.6.0")
    debugImplementation(Dependencies.leakCanary)
    releaseImplementation(Dependencies.leakCanaryNoOp)

    // Testing
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.hamcrest)
    testImplementation(Dependencies.mockito)
    testImplementation(Dependencies.robolectric)
}