package com.bubbble.tests.mockserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.URLDecoder

class MockRequestsDispatcher : Dispatcher() {

    private val handlers = mutableMapOf<String, MockResponseHandler>()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path!!
            .substring(1) //remove slash from start of path
            .substringBefore("?")
            .let { URLDecoder.decode(it, "UTF-8") }
        val handler = handlers[path] ?: throw IllegalArgumentException("Response for \"$path\" not found")
        return handler.handle(request)
    }

    fun addPathHandler(path: String, handler: MockResponseHandler) {
        handlers[path] = handler
    }

}