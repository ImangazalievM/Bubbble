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

        val dribbble_client_id: String by project
        val dribbble_client_secret: String by project
        val dribbble_client_access_token: String by project
        buildConfigField("String", "DRIBBBLE_CLIENT_ID", "\"${dribbble_client_id}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_SECRET", "\"${dribbble_client_secret}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_ACCESS_TOKEN", "\"${dribbble_client_access_token}\"")
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
    api(project(Modules.Core.models))
    api(project(Modules.Core.network))

    kapt(Dependencies.daggerCompiler)

    api(Dependencies.coroutinesCore)
    api(Dependencies.coroutinesAndroid)

    implementation(Dependencies.jsoup)
}