package com.bubbble.data.global.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class CommonPagingSource<T: Any> @Inject constructor(
    private val dataSource: suspend (params: PagingParams) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, T> {
        return try {
            val pagingParams = params.pagingParams
            LoadResult.Page(
                data = dataSource(pagingParams),
                prevKey = null, // Only paging forward.
                nextKey = pagingParams.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}