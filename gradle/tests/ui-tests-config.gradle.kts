import com.android.build.gradle.TestedExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope

configure<TestedExtension> {
    //testBuildType = "uiTest"

    buildTypes {
        //create("uiTest") { }
    }

    sourceSets {
        //getByName("uiTest").resources.srcDirs("src/test/resources")
        getByName("androidTest").resources.srcDirs("src/test/resources")
    }
}

val androidTestImplementation by configurations

dependencies {
    androidTestImplementation(Dependencies.Tests.okhttpMockServer)
    androidTestImplementation(Dependencies.Tests.jsonObject)

    androidTestImplementation(Dependencies.Tests.junit)
    androidTestImplementation(Dependencies.Tests.rules)
    androidTestImplementation(Dependencies.Tests.kakao)
    androidTestImplementation(Dependencies.Tests.kaspresso)
    androidTestImplementation(Dependencies.Tests.okHttpIdlingResource)

    androidTestImplementation(project(Modules.tests))
}