package com.bubbble.data.global.parsing

import com.bubbble.data.global.paging.PagingParams
import okhttp3.HttpUrl
import org.jsoup.nodes.Element

abstract class PageParser<Params, Data> {

    abstract fun getUrl(
        baseUrl: String,
        params: Params,
        pagingParams: PagingParams
    ): HttpUrl

    abstract fun parseHtml(
        html: String,
        baseUrl: String,
        pageUrl: String
    ): Data

    fun Element.getText(selector: String): String {
        return select(selector).firstOrNull()?.text() ?: ""
    }

    fun Element.getElement(selector: String): Element {
        return select(selector).first()
    }

    fun Element.getElementOrNull(selector: String): Element? {
        return select(selector).first()
    }

    fun String.remove(symbol: String): String {
        return replace(symbol.toRegex(), "")
    }

}