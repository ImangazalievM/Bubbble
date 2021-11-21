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
    implementation(project(Modules.AppMvp.featureUserProfile))

    kapt(Dependencies.Mvp.moxyCompiler)

    // Developer Tools
    debugImplementation(Dependencies.DevTools.stetho)
    debugImplementation(Dependencies.DevTools.stethoOkHttp)
    debugImplementation(Dependencies.DevTools.okHttpLogging)
    debugImplementation(Dependencies.DevTools.ok2curl)
    debugImplementation(Dependencies.DevTools.leakCanary)
    releaseImplementation(Dependencies.DevTools.leakCanaryNoOp)
}