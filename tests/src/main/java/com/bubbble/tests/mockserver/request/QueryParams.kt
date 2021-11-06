package com.bubbble.tests.mockserver.request

import com.bubbble.tests.extensions.assertEquals
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import kotlin.jvm.Throws

class QueryParams(path: String) {

    private val params: Map<String, String>

    init {
        params = getUrlParameters(path)
    }

    val count = params.size

    fun get(key: String) = params[key]

    fun getBool(key: String) = get(key).toBoolean()

    fun getInt(key: String) = get(key)?.toInt()

    fun getLong(key: String) = get(key)?.toLong()

    fun getFloat(key: String) = get(key)?.toFloat()

    fun assertEquals(key: String, value: String) {
        get(key).assertEquals(value)
    }

    fun assertEquals(key: String, value: Int) {
        getInt(key).assertEquals(value)
    }

    fun assertEquals(key: String, value: Long) {
        getLong(key).assertEquals(value)
    }

    fun assertEquals(key: String, value: Boolean) {
        getBool(key).assertEquals(value)
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getUrlParameters(url: String): MutableMap<String, String> {
        val params: MutableMap<String, String> = HashMap()
        val urlParts = url.split("?").toTypedArray()
        if (urlParts.size > 1) {
            val query = urlParts[1]
            for (param in query.split("&").toTypedArray()) {
                val pair = param.split("=").toTypedArray()
                val key: String = URLDecoder.decode(pair[0], "UTF-8")
                var value = ""
                if (pair.size > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8")
                }
                params[key] = value
            }
        }
        return params
    }

}