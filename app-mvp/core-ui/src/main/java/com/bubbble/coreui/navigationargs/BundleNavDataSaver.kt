package com.bubbble.coreui.navigationargs

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

object BundleNavDataSaver {

    fun <T> setData(intent: Intent, data: T) {
        val bundle = intent.extras ?: Bundle()
        bundle.putScreenData(data)
        intent.putExtras(bundle)
    }

    fun <T> setData(fragment: Fragment, data: T) {
        val bundle = fragment.arguments ?: Bundle()
        bundle.putScreenData(data)
        fragment.arguments = bundle
    }

    fun <T> setData(bundle: Bundle, data: T) {
        bundle.putScreenData(data)
    }

    private fun <T> Bundle.putScreenData(data: T) {
        if (data is Parcelable) {
            putParcelable(NAV_DATA_KEY, data)
            return
        }

        if (data is Serializable) {
            putSerializable(NAV_DATA_KEY, data)
            return
        }

        throw IllegalArgumentException("Screen data class must be Parcelable or Serializable")
    }

}