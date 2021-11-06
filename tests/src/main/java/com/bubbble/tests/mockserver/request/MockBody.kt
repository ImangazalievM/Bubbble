package com.bubbble.tests.mockserver.request

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okio.Buffer
import com.bubbble.tests.extensions.assertEquals
import com.bubbble.tests.extensions.readTextFile
import java.util.*

class MockBody(
    private val gson: Gson,
        private val body: Buffer
) {

    val asString = body.readUtf8()

    val asJson: JsonElement
        get() = JsonParser.parseString(asString)

    fun assertJson(jsonPath: String) {
        fun String.toSortedJson() : String{
            val map: TreeMap<*, *>? = gson.fromJson(this, TreeMap::class.java)
            return gson.toJson(map)
        }

        val expectedJson = readTextFile(jsonPath).toSortedJson()
        expectedJson.assertEquals(asString.toSortedJson())
    }

    fun assertValue(vararg path: Any, value: String) {
        getElement(path).asString.assertEquals(value)
    }

    fun assertValue(vararg path: Any, value: Int) {
        getElement(path).asInt.assertEquals(value)
    }

    fun assertValue(vararg path: Any, value: Long) {
        getElement(path).asLong.assertEquals(value)
    }

    fun assertValue(vararg path: Any, value: Boolean) {
        getElement(path).asBoolean.assertEquals(value)
    }

    private fun getElement(
            path: Array<out Any>
    ): JsonElement {
        val rootJsonElement = asJson
        if (rootJsonElement.isJsonNull) {
            throw IllegalArgumentException("JSON is null")
        }

        var element: JsonElement = rootJsonElement
        (path).forEach { pathElement ->
            element = getElementChild(element, pathElement)
        }
        return element
    }

    private fun getElementChild(parent: JsonElement, key: Any): JsonElement {
        return when (key) {
            is String -> {
                parent.asJsonObject.get(key)
            }
            is Int -> {
                parent.asJsonArray[key]
            }
            else -> throw IllegalArgumentException("Incorrect body path item: $key")
        }
    }

}