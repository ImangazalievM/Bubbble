package com.bubbble.core.network.exceptions

import java.io.IOException

class NoNetworkException(
        message: String? = null, error: Throwable? = null
) : IOException(message, error)