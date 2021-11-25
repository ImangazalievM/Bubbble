import com.android.build.gradle.BaseExtension

apply {
    plugin("com.android.application")
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