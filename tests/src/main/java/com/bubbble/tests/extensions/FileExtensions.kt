package com.bubbble.tests.extensions

fun Any.readTextFile(path: String): String {
    val loader = javaClass.classLoader!!
    return loader.getResourceAsStream(path).reader().readText()
}