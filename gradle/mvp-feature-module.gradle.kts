import org.jetbrains.kotlin.gradle.plugin.KaptExtension

apply {
    plugin("kotlin-android-extensions")
}
baseScript("module-config")

configure<KaptExtension> {
    arguments {
        //set unique package for MoxyReflector class in each module
        val moxyReflectorPackage = project.name
            .replace("feature-", "")
            .replace("-", "_")
        arg("moxyReflectorPackage", "com.bubbble.ui.$moxyReflectorPackage")
    }
}

//configure<AndroidExtensionsExtension>() {
//    isExperimental = true
//}