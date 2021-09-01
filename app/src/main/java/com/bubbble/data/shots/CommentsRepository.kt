package com.bubbble.data.shots

import com.bubbble.core.DribbbleApi
import com.bubbble.domain.global.models.Comment
import com.bubbble.domain.global.models.ShotCommentsRequestParams
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val dribbbleApi: com.bubbble.core.DribbbleApi) {

    suspend fun getComments(requestParams: ShotCommentsRequestParams): List<Comment> {
        return dribbbleApi.getShotComments(
            requestParams.shotId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}