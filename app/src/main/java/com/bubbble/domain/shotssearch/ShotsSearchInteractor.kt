package com.bubbble.domain.shotssearch

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.models.Shot
import com.bubbble.models.ShotsSearchParams
import javax.inject.Inject

class ShotsSearchInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun search(params: com.bubbble.models.ShotsSearchParams): List<com.bubbble.models.Shot> {
        return shotsRepository.search(params)
    }

}