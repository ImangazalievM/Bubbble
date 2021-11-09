package com.bubbble.data.shots.feed

import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.network.Dribbble
import com.bubbble.data.shots.parser.CommonShotsPageParser
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

class FeedShotsParser @Inject constructor(
    gson: Gson
) : CommonShotsPageParser<ShotsFeedParams>(gson) {

    override fun getUrl(dribbbleUrl: String, params: ShotsFeedParams): HttpUrl {
        return Dribbble.URL.toHttpUrl()
            .newBuilder()
            .addQueryParameter("page", params.page.toString())
            .addQueryParameter("per_page", params.pageSize.toString())
            .build()
    }

}