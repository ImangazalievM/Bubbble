apply {
    plugin("kotlin-kapt")
}

val implementation by configurations
val kapt by configurations

dependencies {
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
}

apply(plugin = Build.Plugins.hilt)