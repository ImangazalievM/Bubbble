import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import com.android.build.gradle.BaseExtension

apply {
    plugin("kotlin-kapt")
    plugin("kotlin-android-extensions")
}
baseScriptFrom(BuildScript.moduleConfig)
baseScriptFrom(BuildScript.hiltConfig)

configure<BaseExtension> {
    buildFeatures.viewBinding = true
}

val kapt by configurations
dependencies {
    kapt(Dependencies.Mvp.moxyCompiler)
}