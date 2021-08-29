package com.imangazalievm.bubbble.domain.global.models

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