package com.bubbble.data.shots.parser

import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.UserType
import com.bubbble.data.global.parsing.PageParser
import com.google.gson.Gson
import org.jsoup.Jsoup
import java.util.*

abstract class CommonShotsPageParser<Params> constructor(
    private val gson: Gson
) : PageParser<Params, List<Shot>>() {

    override fun parseHtml(html: String, baseUrl: String): List<Shot> {
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
            Shot(
                id = shot.id,
                title = shot.title,
                width = 100,
                height = 100,
                imageUrl = info.imageUrl,
                viewsCount = shot.viewCountInt,
                likesCount = shot.likesCountInt,
                commentsCount = shot.commentsCountInt,
                createdAt = null,
                updatedAt = null,
                shotUrl = baseUrl + shot.path,
                user = Shot.User(
                    name = info.userName,
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
        val shotsJson = shotsJsArray.replace(JS_OBJECT_KEY_REGEX.toRegex(), "\"\$1\"$2")
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
            val userName = userInfoLink.getElement("span.display-name").text()
            val userAvatarUrl = userInfoLink.getElement("img.photo").attr("data-src")
            val userUrl = baseUrl + userInfoLink.attr("href")

            val badgeText = userInfoBlock.getElementOrNull("span.badge")?.text()
            val userType = when (badgeText) {
                "Team" -> UserType.TEAM
                "Pro" -> UserType.PRO
                else -> UserType.DEFAULT
            }
            shotId to ShotAdditionalRaw(
                imageUrl = shotImageUrl,
                shotUrl = shotUrl,
                userName = userName,
                userAvatarUrl = userAvatarUrl,
                userUrl = userUrl,
                userType = userType
            )
        }.toMap()
    }

    companion object {
        private const val JS_OBJECT_REGEX =
            "(\\{(?:(?>[^{}\"'\\/]+)|(?>\"(?:(?>[^\\\\\"]+)|\\\\.)*\")|(?>'(?:(?>[^\\\\']+)|\\\\.)*')|(?>\\/\\/.*\\n)|(?>\\/\\*.*?\\*\\/))*\\})"
        private const val JS_OBJECT_REGEX_ORIG =
            "(\\{(?:(?>[^{}\"'\\/]+)|(?>\"(?:(?>[^\\\\\"]+)|\\\\.)*\")|(?>'(?:(?>[^\\\\']+)|\\\\.)*')|(?>\\/\\/.*\\n)|(?>\\/\\*.*?\\*\\/)|(?-1))*\\})"
        private const val JS_ARRAY_REGEX = "\\[($JS_OBJECT_REGEX(,?))*\\]"
        private const val JS_SHOTS_VAR = "var newestShots ="
        private const val JS_SHOTS_REGEX = "$JS_SHOTS_VAR $JS_ARRAY_REGEX"
        private const val JS_OBJECT_KEY_REGEX = "([a-z_]*)(\\:.*,?\\n)"
    }

}