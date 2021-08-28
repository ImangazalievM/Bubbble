package com.imangazalievm.bubbble.presentation.global.utils

import android.widget.Toast
import com.imangazalievm.bubbble.BubbbleApplication
import com.imangazalievm.bubbble.BuildConfig

object DebugUtils {

    @JvmStatic
    fun showDebugErrorMessage(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
            Toast.makeText(BubbbleApplication.instance, throwable.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

}