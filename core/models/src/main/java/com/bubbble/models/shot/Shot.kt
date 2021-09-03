package com.bubbble.models.shot

import com.bubbble.models.user.Team
import com.bubbble.models.user.User
import java.util.*

class Shot(
    val id: Long,
    val title: String,
    val description: String,
    val width: Int,
    val height: Int,
    val images: Images,
    val viewsCount: Int,
    val likesCount: Int,
    val bucketsCount: Int,
    val commentsCount: Int,
    val createdAt: Date?,
    val updatedAt: Date?,
    val htmlUrl: String,
    val reboundSourceUrl: String,
    val tags: List<String>,
    val user: User,
    val team: Team?,
    val isAnimated: Boolean
)