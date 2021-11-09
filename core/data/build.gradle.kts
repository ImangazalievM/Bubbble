plugins {
    kotlin("kapt")
}

android {
    defaultConfig {
        val dribbble_client_id: String by project
        val dribbble_client_secret: String by project
        val dribbble_client_access_token: String by project
        buildConfigField("String", "DRIBBBLE_CLIENT_ID", "\"${dribbble_client_id}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_SECRET", "\"${dribbble_client_secret}\"")
        buildConfigField("String", "DRIBBBLE_CLIENT_ACCESS_TOKEN", "\"${dribbble_client_access_token}\"")
    }
}

dependencies {
    implementation(project(Modules.Core.di))
    api(project(Modules.Core.models))
    api(project(Modules.Core.network))

    api(Dependencies.coroutinesCore)
    api(Dependencies.coroutinesAndroid)

    implementation(Dependencies.jsoup)
}