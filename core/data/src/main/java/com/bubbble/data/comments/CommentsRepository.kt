package com.bubbble.data.comments

import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.ShotCommentsParams
import com.bubbble.core.network.DribbbleApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getComments(
        requestParams: ShotCommentsParams
    ): List<Comment> {
        TODO()
        //https://dribbble.com/shots/16933705/comments?page=1&sort=recent&format=json
    }

}