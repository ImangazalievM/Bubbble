package com.imangazalievm.bubbble.domain.shotdetails

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.data.shots.CommentsRepository
import com.imangazalievm.bubbble.data.shots.ImagesRepository
import com.imangazalievm.bubbble.domain.global.models.Comment
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams
import io.reactivex.Completable
import io.reactivex.Single
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