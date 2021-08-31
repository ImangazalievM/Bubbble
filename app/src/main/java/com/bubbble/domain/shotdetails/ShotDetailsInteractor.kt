package com.bubbble.domain.shotdetails

import com.bubbble.data.shots.CommentsRepository
import com.bubbble.data.shots.ImagesRepository
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.domain.global.models.Comment
import com.bubbble.domain.global.models.Shot
import com.bubbble.domain.global.models.ShotCommentsRequestParams
import javax.inject.Inject

class ShotDetailsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository,
    private val commentsRepository: CommentsRepository,
    private val imagesRepository: ImagesRepository
) {

    suspend fun getShot(shotId: Long): Shot {
        return shotsRepository.getShot(shotId)
    }

    suspend fun getShotComments(
        shotCommentsRequestParams: ShotCommentsRequestParams
    ): List<Comment> {
        return commentsRepository.getComments(shotCommentsRequestParams)
    }

    suspend fun saveImage(imageUrl: String) {
        return imagesRepository.saveImage(imageUrl)
    }

}