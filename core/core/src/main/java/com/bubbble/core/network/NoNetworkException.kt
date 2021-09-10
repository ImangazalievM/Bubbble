package com.bubbble.core.network

import java.io.IOException

class NoNetworkException(
        message: String? = null,
        error: Throwable? = null
) : IOException(message, error)