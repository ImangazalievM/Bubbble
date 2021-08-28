package com.imangazalievm.bubbble.domain.shotslist

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.global.models.Shot
import io.reactivex.Single
import javax.inject.Inject

class ShotsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository,
    private val schedulersProvider: SchedulersProvider
) {

    fun getShots(shotsRequestParams: ShotsRequestParams): Single<List<Shot>> {
        return shotsRepository.getShots(shotsRequestParams)
            .subscribeOn(schedulersProvider.io())
    }

}