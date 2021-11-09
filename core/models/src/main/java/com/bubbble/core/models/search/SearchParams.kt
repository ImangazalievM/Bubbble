package com.bubbble.core.models.search

import com.bubbble.core.models.shot.ShotSortType

class SearchParams(
    val searchQuery: String,
    val searchType: SearchType = SearchType.SHOT,
    val sort: ShotSortType,
    val page: Int,
    val pageSize: Int
)