package com.bubbble.tests.mockserver.request

import com.google.gson.Gson
import okhttp3.mockwebserver.RecordedRequest

class MockRequest(
    private val gson: Gson,
    private val request: RecordedRequest
) {

    val path: String by lazy { request.path!! }
    val method: String? by lazy { request.method }
    val queryParams: QueryParams by lazy { QueryParams(path) }
    val body: MockBody by lazy { MockBody(gson, request.body) }

    fun getHeader(name: String) = request.getHeader(name)

}