package com.bubbble.domain.shotdetails

import com.bubbble.data.shots.CommentsRepository
import com.bubbble.data.shots.ImagesRepository
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.core.models.Comment
import com.bubbble.core.models.Shot
import com.bubbble.core.models.ShotCommentsRequestParams
import javax.inject.Inject

class ShotDetailsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository,
    private val commentsRepository: CommentsRepository,
    private val imagesRepository: ImagesRepository
) {

    suspend fun getShot(shotId: Long): com.bubbble.core.models.Shot {
        return shotsRepository.getShot(shotId)
    }

    suspend fun getShotComments(
        shotCommentsRequestParams: com.bubbble.core.models.ShotCommentsRequestParams
    ): List<com.bubbble.core.models.Comment> {
        return commentsRepository.getComments(shotCommentsRequestParams)
    }

    suspend fun saveImage(imageUrl: String) {
        return imagesRepository.saveImage(imageUrl)
    }

}