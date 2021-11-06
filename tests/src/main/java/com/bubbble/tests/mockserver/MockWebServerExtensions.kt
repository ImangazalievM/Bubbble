package com.bubbble.tests.mockserver

import com.bubbble.tests.extensions.readTextFile
import com.lectra.koson.ArrayType
import com.lectra.koson.ObjectType
import io.kotest.core.spec.style.scopes.DescribeScope
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import com.bubbble.tests.mockserver.request.BubbbleMockWebServer

private val webServers = mutableMapOf<Any, BubbbleMockWebServer>()

fun DescribeScope.initWebServer(
        configure: BubbbleMockWebServer.() -> Unit
): BubbbleMockWebServer {
    val serverKey = this
    beforeEach {
        val webServer = BubbbleMockWebServer()
        webServers[serverKey] = webServer
        webServer.configure()
        webServer.start()
    }
    afterEach {
        webServers[serverKey]!!.shutdown()
    }
    return BubbbleMockWebServer()
}

suspend fun DescribeScope.apiTest(
        description: String,
        body: suspend ApiTest.() -> Unit
) {
    val serverKey = this
    it(description) {
        val webServer = webServers[serverKey] ?: throw IllegalStateException("WebServer not initialized")
        val apiTest = ApiTest(webServer)
        runBlocking { body(apiTest) }
    }
}

fun MockResponse.setBodyFromFile(
        jsonFilePath: String,
        responseCode: Int = 200
) {
    setBody(readTextFile(jsonFilePath))
    setResponseCode(responseCode)
}


fun MockResponse.setJsonBody(
        body: ObjectType,
        responseCode: Int = 200
) {
    setBody(body.toString())
    setResponseCode(responseCode)
}

fun MockResponse.setJsonBody(
        body: ArrayType,
        responseCode: Int = 200
) {
    setBody(body.toString())
    setResponseCode(responseCode)
}