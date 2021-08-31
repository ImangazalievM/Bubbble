package com.bubbble.domain.shotssearch

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.domain.global.models.Shot
import com.bubbble.domain.global.models.ShotsSearchParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(params: ShotsSearchParams): List<Shot> {
        return shotsRepository.search(params)
    }

}