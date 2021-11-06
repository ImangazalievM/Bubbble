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

    api(Dependencies.moxy)
    api(Dependencies.moxyAndroid)
    api(Dependencies.moxyKtx)

    api(Dependencies.coroutinesCore)
    api(Dependencies.coroutinesAndroid)

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)

    //permissions request
    api("com.afollestad.assent:core:3.0.0-RC4")
    api("com.afollestad.assent:rationales:3.0.0-RC4")
}