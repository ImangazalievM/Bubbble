import org.jetbrains.kotlin.gradle.plugin.KaptExtension

apply {
    plugin("kotlin-kapt")
    plugin("kotlin-android-extensions")
}
baseScript("module-config")


val kapt by configurations
dependencies {
    kapt(Dependencies.moxyCompiler)
}