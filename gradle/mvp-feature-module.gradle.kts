import org.jetbrains.kotlin.gradle.plugin.KaptExtension

apply {
    plugin("kotlin-kapt")
    plugin("kotlin-android-extensions")
}
baseScriptFrom(BuildScript.moduleConfig)
baseScriptFrom(BuildScript.hiltConfig)

val kapt by configurations
dependencies {
    kapt(Dependencies.moxyCompiler)
}