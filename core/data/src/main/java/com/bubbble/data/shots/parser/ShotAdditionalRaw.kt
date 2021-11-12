package com.bubbble.data.shots.parser

import com.bubbble.core.models.shot.UserType

data class ShotAdditionalRaw(
    val imageUrl: String,
    val shotUrl: String,
    val userName: String,
    val userDisplayName: String,
    val userAvatarUrl: String,
    val userUrl: String,
    val userType: UserType
)