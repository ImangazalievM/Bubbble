package com.bubbble.data.global.parsing

class PageDownloadException(
    message: String,
    val httpCode: Int,
    val response: String?,
) : RuntimeException(message) {

}