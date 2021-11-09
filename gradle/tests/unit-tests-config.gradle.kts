apply(plugin="de.mannodermaus.android-junit5")

tasks.withType<Test> {
    useJUnitPlatform()
}

val testImplementation by configurations

dependencies {
    testImplementation(Dependencies.Tests.kotlinReflect)
    testImplementation(kotlin(Dependencies.Tests.stdJdk))

    testImplementation(Dependencies.Tests.kotestRunner)
    testImplementation(Dependencies.Tests.kotestCore)
    testImplementation(Dependencies.Tests.mockk)
    testImplementation(Dependencies.Tests.strikt)

    testImplementation(Dependencies.Tests.okhttpMockServer)
    testImplementation(Dependencies.Tests.jsonObject)

    testImplementation(project(Modules.tests))
}