buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:1.4.0")
    }
}

allprojects {
    repositories {
        maven { url = uri("https://maven.google.com" ) }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://dl.bintray.com/greenfrvr/maven/") }
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}