package com.bubbble.data.shots

import com.bubbble.core.DribbbleApi
import com.bubbble.models.Comment
import com.bubbble.models.ShotCommentsRequestParams
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val dribbbleApi: com.bubbble.core.DribbbleApi) {

    suspend fun getComments(requestParams: com.bubbble.models.ShotCommentsRequestParams): List<com.bubbble.models.Comment> {
        return dribbbleApi.getShotComments(
            requestParams.shotId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}