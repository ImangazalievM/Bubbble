import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

apply(plugin="com.android.application")
//plugin {
//    id("com.android.application")
//    //id("kotlin-android-extensions")
//}
//apply {
//    plugin("com.android.application")
//    //plugin("kotlin-android-extensions")
//}
baseScript("base-config")

//android {
//    buildFeatures.viewBinding = true
//}
//
//androidExtensions {
//    isExperimental = true
//}

configure<BaseExtension> {
    defaultConfig {
        applicationId = "com.bubbble"
        versionCode = Build.Versions.appVersionCode
        versionName = Build.Versions.appVersion

        multiDexEnabled = true
    }

    buildFeatures.viewBinding = true
}

//configure<AndroidExtensionsExtension>() {
//    isExperimental = true
//}