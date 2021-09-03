package com.bubbble.models

import com.bubbble.models.user.User
import java.util.*

class Comment(
    val id: Long,
    val user: User,
    val body: String,
    val createdAt: Date,
    val updatedAt: Date,
    val likesUrl: String,
    val likeCount: Int
)