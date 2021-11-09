package com.bubbble.shots.shotslist

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.feed.ShotsFeedParams
import javax.inject.Inject

class ShotsInteractor @Inject constructor(
    private val shotsRepository: ShotsRepository
) {

    suspend fun getShots(shotsRequestParams: ShotsFeedParams): List<Shot> {
        return shotsRepository.getShots(shotsRequestParams)
    }

}