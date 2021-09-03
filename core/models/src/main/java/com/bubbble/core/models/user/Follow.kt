package com.bubbble.core.models.user

import java.util.*

class Follow(
    val id: Long,
    val dateCreated: Date,
    val follower: User
)