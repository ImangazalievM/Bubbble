package com.bubbble.data.shots

import android.text.TextUtils
import android.util.Log
import com.bubbble.Constants
import com.bubbble.core.Dribbble
import com.bubbble.data.global.parsing.PageParser
import com.bubbble.domain.global.models.*
import okhttp3.HttpUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class SearchPageParser @Inject constructor(

) : PageParser<ShotsSearchParams, List<Shot>>() {

    override fun getUrl(dribbbleUrl: String, params: ShotsSearchParams): HttpUrl {
        return com.bubbble.core.Dribbble.Search.path.toHttpUrl()
            .newBuilder()
            .addQueryParameter("q", params.searchQuery)
            //.addQueryParameter("s", sort)
            .addQueryParameter("page", params.page.toString())
            .addQueryParameter("per_page", params.pageSize.toString())
            .build()
    }

    override fun parseHtml(html: String): List<Shot> {
        val shotElements = Jsoup.parse(html, com.bubbble.core.Dribbble.URL).select("li[id^=screenshot]")
        val shots: MutableList<Shot> = ArrayList(shotElements.size)
        for (element in shotElements) {
            val shot = parseShot(element, DATE_FORMAT)
            shots.add(shot)
        }
        return shots.toList()
    }

    private fun parseShot(element: Element, dateFormat: SimpleDateFormat): Shot {
        val descriptionBlock = element.getElement("a.dribbble-over")
        // API responses wrap description in a <p> tag. Do the same for consistent display.
        var description = descriptionBlock.select("span.comment").text().trim { it <= ' ' }
        if (!TextUtils.isEmpty(description)) {
            description = "<p>$description</p>"
        }
        var imgUrl = element.select("img").first().attr("src")
        if (imgUrl.contains("_teaser.")) {
            imgUrl = imgUrl.replace("_teaser.", ".")
        }
        var createdAt: Date? = null
        try {
            createdAt = dateFormat.parse(descriptionBlock.getText("em.timestamp"))
        } catch (e: ParseException) {
        }
        Log.d(Constants.TAG, "search: $imgUrl")
        return Shot(
            id = element.id().replace("screenshot-", "").toLong(),
            title = com.bubbble.core.Dribbble.URL + element.getElement("a.dribbble-link").attr("href"),
            description = descriptionBlock.getText("strong"),
            width = 100,
            height = 100,
            images = Images(null, null, imgUrl),
            viewsCount = element.getText("li.views").remove(",").toInt(),
            likesCount = element.getText("li.fav").remove(",").toInt(),
            bucketsCount = -1,
            commentsCount = element.getText("li.cmnt").remove(",").toInt(),
            createdAt = createdAt,
            updatedAt = createdAt,
            htmlUrl = "#",
            reboundSourceUrl = "",
            tags = listOf(),
            user = parseUser(element.select("h2").first()),
            isAnimated = element.select("div.gif-indicator").first() != null,
            team = null
        )
    }

    private fun parseUser(element: Element): User {
        val userBlock = element.select("a.url").first()
        var avatarUrl = userBlock.select("img.photo").first().attr("src")
        if (avatarUrl.contains("/mini/")) {
            avatarUrl = avatarUrl.replace("/mini/", "/normal/")
        }
        val matchId = PATTERN_PLAYER_ID.matcher(avatarUrl)
        var id = -1L
        if (matchId.find() && matchId.groupCount() == 1) {
            id = matchId.group(1).toLong()
        }
        val slashUsername = userBlock.attr("href")
        val username = if (TextUtils.isEmpty(slashUsername)) null else slashUsername.substring(1)
        return User(
            id = id,
            name = userBlock.text(),
            username = username ?: "What?!!!",
            htmlUrl = com.bubbble.core.Dribbble.URL + slashUsername,
            avatarUrl = avatarUrl,
            bio = null,
            location = null,
            links = Links("", ""),
            bucketsCount = 0,
            commentsReceivedCount = 0,
            followersCount = 0,
            followingsCount = 0,
            likesCount = 0,
            likesReceivedCount = 0,
            projectsCount = 0,
            reboundsReceivedCount = 0,
            shotsCount = 0,
            teamsCount = 0,
            canUploadShot = false,
            type = "",
            pro = element.select("span.badge-pro").size > 0,
            bucketsUrl = "",
            followersUrl = "",
            followingUrl = "",
            likesUrl = "",
            shotsUrl = "",
            teamsUrl = "",
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    companion object {
        private val PATTERN_PLAYER_ID = Pattern.compile("users/(\\d+?)/", Pattern.DOTALL)
        private val DATE_FORMAT = SimpleDateFormat("MMMM d, yyyy")
    }

}