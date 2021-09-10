package com.bubbble.coreui.mvp

import android.util.Log
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.R
import com.bubbble.coreui.resourcesmanager.ResourcesManager
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import kotlin.reflect.KClass

class ErrorHandler @Inject constructor(
        private val resourcesManager: ResourcesManager
) {

    fun proceed(
            error: Throwable,
            handledErrorTypes: List<KClass<out Throwable>> = emptyList(),
            errorHandler: (Throwable) -> Unit = {},
            messageListener: (String) -> Unit = {}
    ) {
        Log.e("MID", error.message, error)
        if (handledErrorTypes.contains(error::class)) {
            errorHandler(error)
        } else {
            messageListener(getMessageForError(error))
        }
    }

    private fun getMessageForError(error: Throwable): String {
        val messageResId = when (error) {
            is NoNetworkException -> R.string.network_error
            is UnknownHostException -> R.string.check_network_message
            is SSLHandshakeException -> R.string.ssl_error_check_device_time_message
            is IOException -> R.string.network_error
            else -> R.string.unknown_error
        }
        return resourcesManager.getString(messageResId)
    }


}
