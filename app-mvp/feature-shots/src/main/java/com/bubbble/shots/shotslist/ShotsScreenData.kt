package com.bubbble.shots.shotslist

import com.bubbble.core.models.feed.ShotsFeedParams
import java.io.Serializable

class ShotsScreenData(
    val sort: ShotsFeedParams.Sorting
) : Serializable