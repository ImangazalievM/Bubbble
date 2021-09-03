package com.bubbble.domain.shotslist

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.core.models.Shot
import com.bubbble.core.models.ShotsRequestParams
import javax.inject.Inject

class ShotsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun getShots(shotsRequestParams: com.bubbble.core.models.ShotsRequestParams): List<com.bubbble.core.models.Shot> {
        return shotsRepository.getShots(shotsRequestParams)
    }

}