package com.bubbble.ui.navigationargs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

inline fun <reified T> Activity.screenData(): ActivityNavDataProperty<T> {
    return ActivityNavDataProperty()
}

inline fun <reified T> Fragment.screenData(): FragmentNavDataProperty<T> {
    return FragmentNavDataProperty()
}

inline fun <reified T> Intent.getScreenData(): T {
    val extractor = BundleNavDataExtractor()
    return extractor.getData(extras)
}

inline fun <reified T> Activity.getScreenData(): T {
    val extractor = BundleNavDataExtractor()
    return extractor.getData(intent.extras)
}

inline fun <reified T> Fragment.getScreenData(): T {
    val extractor = BundleNavDataExtractor()
    return extractor.getData(arguments)
}

fun <T> Intent.setScreenData(data: T) {
    BundleNavDataSaver.setData(this, data)
}

fun <T> Fragment.setScreenData(data: T) {
    BundleNavDataSaver.setData(this, data)
}

fun <T> Bundle.setScreenData(data: T) {
    BundleNavDataSaver.setData(this, data)
}

inline fun <reified T : Activity> buildIntent(
    context: Context,
    data: Any
): Intent {
    val intent = Intent(context, T::class.java)
    intent.setScreenData(data)
    return intent
}

inline fun <reified T : Fragment> createFragment(
    data: Any
): Fragment {
    val fragment = T::class.java.newInstance()
    fragment.setScreenData(data)
    return fragment
}