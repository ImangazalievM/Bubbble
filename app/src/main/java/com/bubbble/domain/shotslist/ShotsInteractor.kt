package com.bubbble.domain.shotslist

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.models.Shot
import com.bubbble.models.ShotsRequestParams
import javax.inject.Inject

class ShotsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun getShots(shotsRequestParams: com.bubbble.models.ShotsRequestParams): List<com.bubbble.models.Shot> {
        return shotsRepository.getShots(shotsRequestParams)
    }

}