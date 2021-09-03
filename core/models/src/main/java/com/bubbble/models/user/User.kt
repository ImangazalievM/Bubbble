package com.bubbble.models.user

import java.util.*

class User(
    val id: Long,
    val name: String,
    val username: String,
    val htmlUrl: String,
    val avatarUrl: String,
    val bio: String?,
    val location: String?,
    val links: Links,
    val bucketsCount: Int,
    val commentsReceivedCount: Int,
    val followersCount: Int,
    val followingsCount: Int,
    val likesCount: Int,
    val likesReceivedCount: Int,
    val projectsCount: Int,
    val reboundsReceivedCount: Int,
    val shotsCount: Int,
    val teamsCount: Int,
    val canUploadShot: Boolean,
    val type: String,
    val pro: Boolean,
    val bucketsUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val likesUrl: String?,
    val shotsUrl: String,
    val teamsUrl: String,
    val createdAt: Date,
    val updatedAt: Date
)