package com.bubbble.tests.mockserver.request

import okhttp3.mockwebserver.MockWebServer
import com.bubbble.tests.mockserver.MockRequestsDispatcher
import com.bubbble.tests.mockserver.MockResponseHandler
import com.bubbble.tests.mockserver.ResponseBuilder
import com.google.gson.Gson

class BubbbleMockWebServer {

    private val webServer = MockWebServer()
    private val dispatcher = MockRequestsDispatcher()
    private val gson = Gson()

    init {
        webServer.dispatcher = dispatcher
    }

    fun start(port: Int? = null) = webServer.start(port ?: 9999)

    fun shutdown() = webServer.shutdown()

    fun getUrl() = webServer.url("/")

    fun on(path: String, builder: ResponseBuilder) {
        dispatcher.addPathHandler(path, MockResponseHandler(gson, builder))
    }

    fun getRequest() = MockRequest(gson, webServer.takeRequest())

}