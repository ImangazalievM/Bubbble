package com.bubbble.data.shots.details

import com.bubbble.core.models.shot.ShotDetails
import com.bubbble.core.models.shot.ShotDetailsParams
import com.bubbble.core.models.shot.ShotImage
import com.bubbble.core.models.shot.UserType
import com.bubbble.core.network.Dribbble
import com.bubbble.data.global.paging.PagingParams
import com.bubbble.data.global.parsing.PageParser
import com.bubbble.data.global.parsing.ParsingRegex
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

class ShotDetailsParser @Inject constructor(

) : PageParser<ShotDetailsParams, ShotDetails>() {

    override fun getUrl(
        baseUrl: String,
        params: ShotDetailsParams,
        pagingParams: PagingParams
    ): HttpUrl {
        return Dribbble.Shots.shotUrl(baseUrl, params.shotSlug)
    }

    override fun parseHtml(html: String, baseUrl: String, pageUrl: String): ShotDetails {
        val parsedPage = Jsoup.parse(html, baseUrl)
        val urlSlug = pageUrl.toHttpUrl().pathSegments[0]
        val title = parsedPage.getText(".shot-header-title")
        val description = parsedPage.getElement(".shot-description-container").html()
        val imageUrls = getImageUrls(parsedPage)
        val bestResolution = imageUrls.last().imageUrl

        val userLinkElement = parsedPage.getElement(".shot-user-link")
        val userDisplayName = userLinkElement.text()
        val userName = userLinkElement.attr("href")
            .replaceFirst("/", "")
        val userUrl = baseUrl.toHttpUrl().newBuilder()
            .addPathSegment(userName)
            .toString()
        val userAvatar = parsedPage.getElement(".shot-user-avatar img").attr("src")
        return ShotDetails(
            urlSlug = urlSlug,
            title = title,
            description = description,
            imageUrl = bestResolution,
            viewsCount = 0,
            likesCount = 0,
            commentsCount = 0,
            createdAt = null,
            updatedAt = null,
            shotUrl = pageUrl,
            user = ShotDetails.User(
                displayName = userDisplayName,
                userName = userName,
                avatarUrl = userAvatar,
                userUrl = userUrl,
                type = UserType.DEFAULT
            ),
            hasMultipleImages = false,
            isAnimated = false
        )
    }

    private fun getImageUrls(parsedPage: Document): List<ShotImage> {
        return parsedPage.getElement(".media-shot img")
            .attr("srcset")
            .trim()
            .split(",")
            .map {
                //the URL is looking like this:
                //http://dribbble.com/iamges/123.png 300w | http://dribbble.com/iamges/123.png 600w
                val url = it.trim().split(" ").first().trim()
                val imageSize = url.toHttpUrl()
                    .queryParameter("resize")
                    ?.split("x")
                val imageWidth = imageSize?.get(0)?.toInt() ?: 0
                val imageHeight = imageSize?.get(1)?.toInt() ?: 0
                    ShotImage(url, ShotImage.Size(imageWidth, imageHeight))
            }
    }

    companion object {
        private const val JS_SHOTS_VAR = "var newestShots ="
        private const val JS_SHOTS_REGEX = "$JS_SHOTS_VAR ${ParsingRegex.JS_ARRAY_REGEX}"
    }
}