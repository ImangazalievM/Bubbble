package com.bubbble.data.global.paging

import androidx.paging.PagingSource

class PagingParams(
    val page: Int,
    val pageSize: Int
)

val PagingSource.LoadParams<Int>.pagingParams: PagingParams
    get() = PagingParams(key ?: 1, loadSize)