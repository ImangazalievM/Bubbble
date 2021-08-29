package com.imangazalievm.bubbble.domain.global.models

import java.util.*

class User(
    id: Long,
    name: String,
    username: String,
    htmlUrl: String,
    avatarUrl: String,
    bio: String,
    location: String,
    links: Links,
    bucketsCount: Int,
    commentsReceivedCount: Int,
    followersCount: Int,
    followingsCount: Int,
    likesCount: Int,
    likesReceivedCount: Int,
    projectsCount: Int,
    reboundsReceivedCount: Int,
    shotsCount: Int,
    teamsCount: Int,
    canUploadShot: Boolean,
    type: String,
    pro: Boolean,
    bucketsUrl: String,
    followersUrl: String,
    followingUrl: String,
    likesUrl: String?,
    shotsUrl: String,
    teamsUrl: String,
    createdAt: Date,
    updatedAt: Date
)