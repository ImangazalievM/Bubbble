plugins {
    kotlin("kapt")
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

    api(Dependencies.Mvp.moxy)
    api(Dependencies.Mvp.moxyAndroid)
    api(Dependencies.Mvp.moxyKtx)
    api(Dependencies.Mvp.cicerone)

    api(Dependencies.coroutinesCore)
    api(Dependencies.coroutinesAndroid)

    //permissions request
    api("com.afollestad.assent:core:3.0.0-RC4")
    api("com.afollestad.assent:rationales:3.0.0-RC4")
}