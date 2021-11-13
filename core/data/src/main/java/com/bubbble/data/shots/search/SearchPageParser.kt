package com.bubbble.data.shots.search

import com.bubbble.core.models.search.SearchParams
import com.bubbble.core.models.search.SearchType
import com.bubbble.core.network.Dribbble
import com.bubbble.data.global.paging.PagingParams
import com.bubbble.data.shots.parser.CommonShotsPageParser
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

class SearchPageParser @Inject constructor(
    gson: Gson
) : CommonShotsPageParser<SearchParams>(gson) {

    override fun getUrl(
        baseUrl: String,
        params: SearchParams,
        pagingParams: PagingParams
    ): HttpUrl {
        return Dribbble.Search.search(
            query = params.searchQuery,
            type = params.searchType.code
        ).toHttpUrl()
            .newBuilder()
            .addQueryParameter("page", pagingParams.page.toString())
            .addQueryParameter("per_page", pagingParams.pageSize.toString())
            .build()
    }

    private val SearchType.code: String?
        get() = when (this) {
            SearchType.SHOT -> Dribbble.Search.shots
            SearchType.MEMBERS -> Dribbble.Search.users
            SearchType.TEAM -> Dribbble.Search.teams
        }

}