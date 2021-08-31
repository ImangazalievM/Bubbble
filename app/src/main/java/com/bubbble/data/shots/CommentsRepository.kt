package com.bubbble.data.shots

import com.bubbble.data.global.network.DribbbleApi
import com.bubbble.domain.global.models.Comment
import com.bubbble.domain.global.models.ShotCommentsRequestParams
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val dribbbleApi: DribbbleApi) {

    suspend fun getComments(requestParams: ShotCommentsRequestParams): List<Comment> {
        return dribbbleApi.getShotComments(
            requestParams.shotId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}