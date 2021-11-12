package com.bubbble.core.models.shot

import java.util.*

data class Shot(
    val id: Long,
    val title: String,
    val width: Int,
    val height: Int,
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