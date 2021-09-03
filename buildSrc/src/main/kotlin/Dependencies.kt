object Build {

    object Versions {
        const val buildTools = "29.0.3"
        const val compileSdk = 29
        const val minSdk = 21
        const val targetSdk = 29

        const val kotlin = "1.4.0"
    }

}

object Dependencies {

    //android
    const val supportAppCompat = "androidx.appcompat:appcompat:1.2.0"
    const val supportDesign = "com.google.android.material:material:1.0.0"
    const val supportCardView = "androidx.cardview:cardview:1.0.0"
    const val supportAnnotations = "com.android.support:support-annotations:25.4.0"
    const val customTabs = "androidx.browser:browser:1.0.0"

    //architecture
    const val dagger = "com.google.dagger:dagger:2.38.1"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:2.38.1"

    //ui
    const val glide = "com.github.bumptech.glide:glide:3.7.0"
    const val photoView = "com.github.chrisbanes:PhotoView:2.1.3"
    const val materialDrawer = "com.mikepenz:materialdrawer:5.9.4@aar"
    const val hashtagView = "com.github.greenfrvr:hashtag-view:1.3.1"

    const val moxy = "com.arello-mobile:moxy:1.5.2"
    const val moxyAppCompat = "com.arello-mobile:moxy-app-compat:1.5.2"
    const val moxyCompiler = "com.arello-mobile:moxy-compiler:1.5.2"

    const val gson = "com.google.code.gson:gson:2.8.8"
    const val okHttp = "com.squareup.okhttp3:okhttp:3.2.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.2.0"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:2.2.0"
    const val jsoup = "org.jsoup:jsoup:1.7.2"

    // Developer Tools
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:1.5"
    const val leakCanaryNoOp =
        "com.squareup.leakcanary:leakcanary-android-no-op:1.5"

    // Test dependencies
    const val junit = "junit:junit:4.12"
    const val hamcrest = "org.hamcrest:hamcrest-all:1.3"
    const val mockito = "org.mockito:mockito-all:1.10.19"
    const val robolectric = "org.robolectric:robolectric:3.1.2"

    object Versions {
        const val kotlinCoroutineVersion = "1.5.0"
    }

}

object Modules {

    object Core {
        const val models = ":core:models"
        const val network = ":core:network"
    }

}