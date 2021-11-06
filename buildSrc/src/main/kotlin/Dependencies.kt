object Build {

    object Versions {
        const val appVersion = "1.0.0"
        const val appVersionCode = 1

        const val buildTools = "29.0.3"
        const val compileSdk = 30
        const val minSdk = 21
        const val targetSdk = 30

        const val kotlin = "1.4.0"
    }

}

object Dependencies {

    object Versions {
        const val kotlinCoroutineVersion = "1.5.0"
        const val okhttpVersion = "4.5.0"
    }

    //android
    const val supportAppCompat = "androidx.appcompat:appcompat:1.2.0"
    const val supportDesign = "com.google.android.material:material:1.0.0"
    const val supportCardView = "androidx.cardview:cardview:1.0.0"
    const val supportAnnotations = "com.android.support:support-annotations:25.4.0"
    const val customTabs = "androidx.browser:browser:1.0.0"

    //architecture
    const val dagger = "com.google.dagger:dagger:2.38.1"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:2.38.1"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:" +
            Versions.kotlinCoroutineVersion
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" +
            Versions.kotlinCoroutineVersion

    //ui
    const val glide = "com.github.bumptech.glide:glide:3.7.0"
    const val photoView = "com.github.chrisbanes:PhotoView:2.1.3"
    const val materialDrawer = "com.mikepenz:materialdrawer:5.9.4@aar"
    const val hashtagView = "com.github.greenfrvr:hashtag-view:1.3.1"

    const val moxy = "com.arello-mobile:moxy:1.5.2"
    const val moxyAppCompat = "com.arello-mobile:moxy-app-compat:1.5.2"
    const val moxyCompiler = "com.arello-mobile:moxy-compiler:1.5.2"

    const val gson = "com.google.code.gson:gson:2.8.8"
    const val okHttp = "com.squareup.okhttp3:okhttp:4.9.1"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
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

    object Tests {
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:1.4.10"
        const val stdJdk = "stdlib-jdk8"

        //unit-tests
        const val junit = "junit:junit:4.13"
        const val rules = "androidx.test:rules:1.3.0"

        private const val kotestVersion = "4.6.3"
        const val kotestRunner = "io.kotest:kotest-runner-junit5:$kotestVersion"
        const val kotestCore = "io.kotest:kotest-assertions-core:$kotestVersion"
        //mocking
        const val mockk = "io.mockk:mockk:1.11.0"

        //assertions
        const val strikt = "io.strikt:strikt-core:0.28.0"
        const val okhttpMockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpVersion}"

        //other
        const val jsonObject = "org.json:json:20201115"

        //UI-tests
        const val kakao = "io.github.kakaocup:kakao:3.0.2"
        const val kaspresso = "com.kaspersky.android-components:kaspresso:1.2.1"
        const val okHttpIdlingResource = "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0"
    }

}

object Modules {

    const val tests = ":tests"

    object Core {
        const val di = ":core:di"
        const val core = ":core:core"

        const val models = ":core:models"
        const val network = ":core:network"
        const val data = ":core:data"
    }

    object AppMvp {
        const val app = ":app-mvp:app"
        const val coreUi = ":app-mvp:core-ui"
        const val featureShots = ":app-mvp:feature-shots"
        const val featureShotDetails = ":app-mvp:feature-shot-details"
        const val featureShotZoom = ":app-mvp:feature-shot-zoom"
        const val featureShotSearch = ":app-mvp:feature-shot-search"
        const val featureUserProfile = ":app-mvp:feature-user-profile"
    }

}