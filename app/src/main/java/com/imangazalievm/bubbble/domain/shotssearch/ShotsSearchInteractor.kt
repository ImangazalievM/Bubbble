package com.imangazalievm.bubbble.domain.shotssearch

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams
import com.imangazalievm.bubbble.domain.global.models.Shot
import io.reactivex.Single
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository,
    private val schedulersProvider: SchedulersProvider
) {

    fun search(requestParams: ShotsSearchRequestParams): Single<List<Shot>> {
        return shotsRepository.search(requestParams)
            .subscribeOn(schedulersProvider.io())
    }

}