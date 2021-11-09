plugins {
    kotlin("kapt")
}

dependencies {
    api(project(Modules.Core.di))
    api(project(Modules.Core.core))
    api(project(Modules.Core.network))

    // unit-tests
    implementation(Dependencies.Tests.kotlinReflect)
    implementation(kotlin(Dependencies.Tests.stdJdk))

    implementation(Dependencies.Tests.kotestRunner)
    implementation(Dependencies.Tests.kotestCore)
    implementation(Dependencies.Tests.mockk)
    implementation(Dependencies.Tests.strikt)

    implementation(Dependencies.Tests.okhttpMockServer)
    implementation(Dependencies.Tests.jsonObject)

    implementation(Dependencies.Tests.junit)
    implementation(Dependencies.Tests.rules)
    implementation(Dependencies.Tests.kakao)
    implementation(Dependencies.Tests.kaspresso)

    api("com.lectra:koson:1.1.0")
}
