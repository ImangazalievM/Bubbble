package com.bubbble.data.shots.parser

import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.UserType
import com.bubbble.core.network.Dribbble
import com.bubbble.data.global.parsing.PageParser
import com.bubbble.data.global.parsing.ParsingRegex
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import java.util.*

abstract class CommonShotsPageParser<Params> constructor(
    private val gson: Gson
) : PageParser<Params, List<Shot>>() {

    override fun parseHtml(html: String, baseUrl: String, pageUrl: String): List<Shot> {
        val shots = parseShots(html)
        val shotAdditionalInfo = extractAdditionalShotsInfo(html, baseUrl)
        return bindShots(shots, shotAdditionalInfo, baseUrl)
    }

    private fun bindShots(
        shots: List<ShotRaw>,
        shotAdditionalInfo: Map<Long, ShotAdditionalRaw>,
        baseUrl: String
    ): List<Shot> {
        return shots.map { shot ->
            val info = shotAdditionalInfo[shot.id]!!
            val shotSlug = shot.path.substringAfterLast("/")
            val shotUrl = Dribbble.Shots.shotUrl(baseUrl, shotSlug)
            Shot(
                id = shot.id,
                shotSlug = shotSlug,
                title = shot.title,
                imageUrl = info.imageUrl,
                viewsCount = shot.viewCountInt,
                likesCount = shot.likesCountInt,
                commentsCount = shot.commentsCountInt,
                createdAt = null,
                updatedAt = null,
                shotUrl = shotUrl.toString(),
                user = Shot.User(
                    displayName = info.userDisplayName,
                    userName = info.userName,
                    avatarUrl = info.userAvatarUrl,
                    userUrl = info.userUrl,
                    type = info.userType
                ),
                hasMultipleImages = false,
                isAnimated = false,//element.select("div.gif-indicator").first() != null,
            )
        }
    }

    private fun parseShots(html: String): List<ShotRaw> {
        val regex = JS_SHOTS_REGEX.toRegex()
        val shotsJsArrayMatchResult = regex.find(html) ?: throw ShotsDataNotFoundException()
        val shotsJsArray = shotsJsArrayMatchResult.groupValues[0]
            .replace(JS_SHOTS_VAR, "")
            .trim()
        val shotsJson = shotsJsArray.replace(ParsingRegex.JS_OBJECT_KEY_REGEX.toRegex(), "\"\$1\"$2")
        return gson.fromJson(shotsJson, Array<ShotRaw>::class.java).toList()
    }

    private fun extractAdditionalShotsInfo(
        html: String,
        baseUrl: String
    ): Map<Long, ShotAdditionalRaw> {
        val parsedPage = Jsoup.parse(html, baseUrl)
        val shotElements = parsedPage.select(".shot-thumbnail")

        return shotElements.map {
            val userInfoBlock = it.getElement(".user-information")
            val shotId = it.attr("data-thumbnail-id").toLong()
            val shotUrl = baseUrl + it.getElement(".shot-thumbnail-link").attr("href")
            val userInfoLink = userInfoBlock.getElement("a[rel=contact]")
            val shotImageUrl = it.getElement("figure.shot-thumbnail-placeholder > img")
                .attr("src")
            val userDisplayName = userInfoLink.getText("span.display-name")
            val userAvatarUrl = userInfoLink.getElement("img.photo").attr("data-src")
            val userName = baseUrl + userInfoLink.attr("href")
                .replaceFirst("/", "")
            val userUrl = baseUrl.toHttpUrl().newBuilder()
                .addPathSegment(userName)
                .toString()

            val badgeText = userInfoBlock.getText("span.badge")
            val userType = when (badgeText) {
                "Team" -> UserType.TEAM
                "Pro" -> UserType.PRO
                else -> UserType.DEFAULT
            }
            shotId to ShotAdditionalRaw(
                imageUrl = shotImageUrl,
                shotUrl = shotUrl,
                userDisplayName = userDisplayName,
                userName = userName,
                userAvatarUrl = userAvatarUrl,
                userUrl = userUrl,
                userType = userType
            )
        }.toMap()
    }

    companion object {
        private const val JS_SHOTS_VAR = "var newestShots ="
        private const val JS_SHOTS_REGEX = "$JS_SHOTS_VAR ${ParsingRegex.JS_ARRAY_REGEX}"
    }
}