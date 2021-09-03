package com.bubbble.domain.shotssearch

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.core.models.Shot
import com.bubbble.core.models.ShotsSearchParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(params: com.bubbble.core.models.ShotsSearchParams): List<com.bubbble.core.models.Shot> {
        return shotsRepository.search(params)
    }

}