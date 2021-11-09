plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.Core.core))
    implementation(project(Modules.Core.models))

    api(Dependencies.okHttp)
    api(Dependencies.retrofit)
    api(Dependencies.retrofitGsonConverter)
}