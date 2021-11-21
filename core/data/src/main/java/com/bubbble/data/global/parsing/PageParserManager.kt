package com.bubbble.data.global.parsing

import com.bubbble.data.di.DribbbleWebSite
import com.bubbble.data.global.paging.PagingParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageParserManager @Inject constructor(
    private val pageDownloader: PageDownloader,
    @DribbbleWebSite
    private val dribbbleUrl: String
) {

    suspend fun <Params, Data> parse(
        parser: PageParser<Params, Data>,
        params: Params,
        pagingParams: PagingParams = PagingParams(1, 1)
    ): Data = withContext(Dispatchers.IO) {
        val pageUrl = parser.getUrl(dribbbleUrl, params, pagingParams)
        val html = pageDownloader.download(pageUrl)
        parser.parseHtml(html, dribbbleUrl, pageUrl.toString())
    }

}