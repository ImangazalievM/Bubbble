package com.bubbble.domain.shotssearch

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotsSearchParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(params: ShotsSearchParams): List<Shot> {
        return shotsRepository.search(params)
    }

}