package com.bubbble.data.shots.parser

import com.google.gson.annotations.SerializedName

data class ShotRaw(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("is_rebound")
    val isRebound: Boolean,
    @SerializedName("rebounds_count")
    val reboundsCount: Int,
    @SerializedName("attachments_count")
    val attachmentsCount: Int,
    @SerializedName("view_count")
    val viewCount: String,
    @SerializedName("comments_count")
    val commentsCount: String,
    @SerializedName("likes_count")
    val likesCount: String,
    @SerializedName("liked")
    val liked: Boolean,
) {

    val viewCountInt: Int = viewCount.normalizeNumber()
    val commentsCountInt: Int = viewCount.normalizeNumber()
    val likesCountInt: Int = viewCount.normalizeNumber()

}

private fun String.normalizeNumber() : Int {
    return replace(".", "")
        .replace("k", "000")
        .toInt()
}