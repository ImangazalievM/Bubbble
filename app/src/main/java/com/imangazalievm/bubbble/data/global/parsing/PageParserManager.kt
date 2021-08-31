package com.imangazalievm.bubbble.data.global.parsing

import com.imangazalievm.bubbble.di.qualifiers.DribbbleWebSite
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageParserManager @Inject constructor(
    private val pageDownloader: PageDownloader,
    @DribbbleWebSite
    private val dribbbleUrl: String
) {

    fun <Params, Data> parse(
        parser: PageParser<Params, Data>,
        params: Params
    ): Data {
        val html = pageDownloader.download(parser.getUrl(dribbbleUrl, params))
        return parser.parseHtml(html)
    }

}