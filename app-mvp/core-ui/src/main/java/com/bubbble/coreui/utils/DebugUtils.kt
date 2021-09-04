package com.bubbble.coreui.utils

import android.widget.Toast
import com.bubbble.BubbbleApplication
import com.bubbble.BuildConfig

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