package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.data.global.network.DribbbleApi
import com.imangazalievm.bubbble.domain.global.models.Comment
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams
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