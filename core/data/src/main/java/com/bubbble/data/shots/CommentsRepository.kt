package com.bubbble.data.shots

import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.ShotCommentsParams
import com.bubbble.core.network.DribbbleApi
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val dribbbleApi: DribbbleApi) {

    suspend fun getComments(
        requestParams: ShotCommentsParams
    ): List<Comment> {
        return dribbbleApi.getShotComments(
            requestParams.shotId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}