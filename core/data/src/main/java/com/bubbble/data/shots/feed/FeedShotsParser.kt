package com.bubbble.data.shots.feed

import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.network.Dribbble
import com.bubbble.data.global.paging.PagingParams
import com.bubbble.data.shots.parser.CommonShotsPageParser
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

class FeedShotsParser @Inject constructor(
    gson: Gson
) : CommonShotsPageParser<ShotsFeedParams>(gson) {

    override fun getUrl(
        baseUrl: String,
        params: ShotsFeedParams,
        pagingParams: PagingParams
    ): HttpUrl {
        return Dribbble.URL.toHttpUrl()
            .newBuilder()
            .addQueryParameter("page", pagingParams.page.toString())
            .addQueryParameter("per_page", pagingParams.pageSize.toString())
            .apply {
                val sortCode = params.sort?.code
                if (sortCode != null) {
                    addPathSegment(Dribbble.Shots.path)
                    addPathSegment(sortCode)
                }
            }
            .build()
    }

    val ShotsFeedParams.Sorting.code: String?
        get() = when (this) {
            ShotsFeedParams.Sorting.POPULAR -> Dribbble.Shots.Sort.popular
            ShotsFeedParams.Sorting.RECENT -> Dribbble.Shots.Sort.recent
            else -> null
        }


}