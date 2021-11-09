import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

apply {
    plugin("com.android.application")
    plugin("kotlin-android-extensions")
    plugin("kotlin-kapt")
}

baseScriptFrom(BuildScript.baseConfig)
baseScriptFrom(BuildScript.hiltConfig)

configure<BaseExtension> {
    defaultConfig {
        applicationId = "com.bubbble"
        versionCode = Build.Versions.appVersionCode
        versionName = Build.Versions.appVersion

        multiDexEnabled = true
    }

    buildFeatures.viewBinding = true
}

configure<AndroidExtensionsExtension>() {
    isExperimental = true
}