buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:1.5.31")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.0.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}

allprojects {
    repositories {
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://dl.bintray.com/greenfrvr/maven/") }
        google()
        jcenter()
        mavenCentral()
    }
}

subprojects {
    configProject(this)
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

fun configProject(project: Project) {
    val urlMatcher = "'(.+)'".toRegex()
    val moduleName = urlMatcher.find(project.displayName)?.groupValues?.getOrNull(1)
    val scripts = BuildScript.getScriptPath(moduleName ?: return)
    project.apply {
        scripts.forEach { scriptPath ->
            from(rootProject.file(scriptPath))
        }
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        val requested = requested
        if (requested.group == "org.jetbrains.kotlin" && requested.name == "kotlin-reflect") {
            useVersion(Build.Versions.kotlin)
        }
    }
}