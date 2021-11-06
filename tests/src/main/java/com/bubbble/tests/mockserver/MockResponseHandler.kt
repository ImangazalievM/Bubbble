package com.bubbble.tests.mockserver

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import com.bubbble.tests.mockserver.request.MockRequest
import com.google.gson.Gson

typealias ResponseBuilder = MockResponse.(request: MockRequest) -> Unit

class MockResponseHandler(
        private val gson: Gson,
        private val body: ResponseBuilder
) {

    fun handle(request: RecordedRequest): MockResponse {
        val mockRequest = MockRequest(gson, request)
        return MockResponse().apply { body(mockRequest) }
    }

}