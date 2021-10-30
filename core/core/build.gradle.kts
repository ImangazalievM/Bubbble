plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Core.di))
    kapt(Dependencies.daggerCompiler)
}