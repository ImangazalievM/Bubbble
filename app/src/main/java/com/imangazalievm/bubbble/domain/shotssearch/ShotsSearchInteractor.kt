package com.imangazalievm.bubbble.domain.shotssearch

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(requestParams: ShotsSearchRequestParams): List<Shot> {
        return shotsRepository.search(requestParams)
    }

}