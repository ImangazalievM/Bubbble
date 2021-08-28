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
    private val imagesRepository: ImagesRepository,
    private val schedulersProvider: SchedulersProvider
) {

    fun getShot(shotId: Long): Single<Shot> {
        return shotsRepository.getShot(shotId)
            .subscribeOn(schedulersProvider.io())
    }

    fun getShotComments(
        shotCommentsRequestParams: ShotCommentsRequestParams
    ): Single<List<Comment>> {
        return commentsRepository.getComments(shotCommentsRequestParams)
            .subscribeOn(schedulersProvider.io())
    }

    fun saveImage(imageUrl: String): Completable {
        return imagesRepository.saveImage(imageUrl)
            .subscribeOn(schedulersProvider.io())
    }

}