package com.bubbble.domain.shotslist

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.domain.global.models.Shot
import com.bubbble.domain.global.models.ShotsRequestParams
import javax.inject.Inject

class ShotsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun getShots(shotsRequestParams: ShotsRequestParams): List<Shot> {
        return shotsRepository.getShots(shotsRequestParams)
    }

}