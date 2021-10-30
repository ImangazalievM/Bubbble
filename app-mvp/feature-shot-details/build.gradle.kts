plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.AppMvp.coreUi))
    kapt(Dependencies.moxyCompiler)

    implementation(Dependencies.hashtagView)

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)
}