package com.bubbble.ui.navigationargs

import android.os.Bundle
import android.os.Parcelable

internal const val NAV_DATA_KEY = "com.bubbble.NAV_DATA_KEY"

class BundleNavDataExtractor {

    fun <ScreenData> getData(bundle: Bundle?) : ScreenData {
        if (bundle == null) throw IllegalArgumentException("Screen data not found")

        val parcelable = bundle.getParcelable<Parcelable>(NAV_DATA_KEY)
        if (parcelable != null) return parcelable as ScreenData

        val serializable = bundle.getSerializable(NAV_DATA_KEY)
        if (serializable != null) return serializable as ScreenData

        throw IllegalArgumentException("Screen data not found")
    }

}