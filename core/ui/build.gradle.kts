plugins {
    kotlin("kapt")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    api(Dependencies.androidxCoreKtx)
    api(Dependencies.androidxAppCompat)
    api(Dependencies.googleMaterial)
    api(Dependencies.paging)
}