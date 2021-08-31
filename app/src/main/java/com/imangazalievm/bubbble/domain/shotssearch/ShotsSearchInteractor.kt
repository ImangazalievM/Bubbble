package com.imangazalievm.bubbble.domain.shotssearch

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(params: ShotsSearchParams): List<Shot> {
        return shotsRepository.search(params)
    }

}