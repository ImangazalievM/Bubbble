dependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.AppMvp.coreUi))

    implementation(Dependencies.hashtagView)

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)
}