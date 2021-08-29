package com.imangazalievm.bubbble.domain.shotzoom

import com.imangazalievm.bubbble.data.shots.ImagesRepository
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import io.reactivex.Completable
import javax.inject.Inject

class ShotZoomInteractor @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend fun saveImage(shotImageUrl: String) {
        imagesRepository.saveImage(shotImageUrl)
    }

}