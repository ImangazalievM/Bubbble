package com.bubbble.core.models.shot

import java.util.*

data class ShotDetails(
    val urlSlug: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val viewsCount: Int,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: Date?,
    val updatedAt: Date?,
    val shotUrl: String,
    val user: User,
    val hasMultipleImages: Boolean,
    val isAnimated: Boolean
) {

    data class User(
        val displayName: String,
        val userName: String,
        val avatarUrl: String,
        val userUrl: String,
        val type: UserType
    )

}