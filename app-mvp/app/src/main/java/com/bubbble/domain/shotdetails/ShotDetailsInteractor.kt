package com.bubbble.domain.shotdetails

import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotCommentsParams
import com.bubbble.data.shots.CommentsRepository
import com.bubbble.data.shots.ImagesRepository
import com.bubbble.data.shots.ShotsRepository
import javax.inject.Inject

class ShotDetailsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository,
    private val commentsRepository: CommentsRepository,
    private val imagesRepository: ImagesRepository
) {

    suspend fun getShot(shotId: Long): Shot {
        return shotsRepository.getShot(shotId)
    }

    suspend fun getShotComments(params: ShotCommentsParams): List<Comment> {
        return commentsRepository.getComments(params)
    }

    suspend fun saveImage(imageUrl: String) {
        return imagesRepository.saveImage(imageUrl)
    }

}