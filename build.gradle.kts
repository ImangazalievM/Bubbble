buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:1.5.21")
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
    val scriptPath = BuildScript.getScriptPath(moduleName ?: return) ?: return
    project.apply {
        from(rootProject.file(scriptPath))
    }
}