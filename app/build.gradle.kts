plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.imangazalievm.bubbble"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 3
        versionName = "1.1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        val dribbble_client_id: String by project
        val dribbble_client_secret: String by project
        val dribbble_client_access_token: String by project
        buildConfigField("String", "DRIBBBLE_CLIENT_ID", "\"${dribbble_client_id}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_SECRET", "\"${dribbble_client_secret}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_ACCESS_TOKEN", "\"${dribbble_client_access_token}\"")
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
    implementation(Dependencies.Libraries.appCompat)

    implementation(Dependencies.Old.supportAppCompat)
    implementation(Dependencies.Old.supportDesign)
    implementation(Dependencies.Old.supportCardView)
    implementation(Dependencies.Old.customTabs)

    implementation(Dependencies.Old.dagger)
    annotationProcessor(Dependencies.Old.daggerCompiler)
    kapt(Dependencies.Old.daggerCompiler)

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.Versions.kotlinCoroutineVersion}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Dependencies.Versions.kotlinCoroutineVersion}")

    implementation(Dependencies.Old.rxJava)
    implementation(Dependencies.Old.rxAndroid)

    implementation(Dependencies.Old.moxy)
    implementation(Dependencies.Old.moxyAppCompat)
    kapt(Dependencies.Old.moxyCompiler)

    implementation(Dependencies.Old.retrofit)
    implementation(Dependencies.Old.retrofitGsonConverter)
    implementation(Dependencies.Old.retrofitRxJavaAdapter)
    implementation(Dependencies.Old.jsoup)

    implementation(Dependencies.Old.glide)
    implementation(Dependencies.Old.photoView)
    implementation(Dependencies.Old.materialDrawer) {
        isTransitive = true
        exclude(group = "com.android.support")
    }
    implementation(Dependencies.Old.hashtagView)

    implementation(Dependencies.Old.rxpermissions)

    // Developer Tools
    debugImplementation("com.facebook.stetho:stetho:1.5.1")
    debugImplementation("com.facebook.stetho:stetho-okhttp3:1.5.1")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    debugImplementation("com.github.mrmike:ok2curl:0.6.0")
    debugImplementation(Dependencies.Old.leakCanary)
    releaseImplementation(Dependencies.Old.leakCanaryNoOp)

    // Testing
    testImplementation(Dependencies.Old.junit)
    testImplementation(Dependencies.Old.hamcrest)
    testImplementation(Dependencies.Old.mockito)
    testImplementation(Dependencies.Old.robolectric)
}