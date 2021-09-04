package com.bubbble.data.global.parsing

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import javax.inject.Inject

abstract class PageParser<Params, Data> {

    abstract fun getUrl(dribbbleUrl: String, params: Params): HttpUrl

    abstract fun parseHtml(html: String): Data

    fun Element.getText(selector: String): String {
        return select(selector).first().child(0).text()
    }

    fun Element.getElement(selector: String): Element {
        return select(selector).first()
    }

    fun String.remove(symbol: String): String {
        return replace(symbol.toRegex(), "")
    }

}