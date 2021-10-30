plugins {
    kotlin("kapt")
}

android {
    signingConfigs {
        create("bubbble") {
            storeFile(rootProject.file("keystore.jks"))
            storePassword = "bubbble"
            keyAlias = "bubbble"
            keyPassword = "bubbble"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("bubbble")
            applicationIdSuffix = ".debug"
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("bubbble")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
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