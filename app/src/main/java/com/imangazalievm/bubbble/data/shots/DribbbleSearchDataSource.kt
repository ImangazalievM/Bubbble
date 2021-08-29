package com.imangazalievm.bubbble.data.shots

import android.text.TextUtils
import android.util.Log
import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.domain.global.models.Images
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.User
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class DribbbleSearchDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val dribbbleUrl: String
) {

    suspend fun search(query: String?, sort: String?, page: Int, pageSize: Int): List<Shot> {

        val searchUrl = "$dribbbleUrl/search".toHttpUrl()
            .newBuilder()
            .addQueryParameter("q", query)
            .addQueryParameter("s", sort)
            .addQueryParameter("page", page.toString())
            .addQueryParameter("per_page", pageSize.toString())
            .build()
        val searchRequest: Request = Request.Builder().url(searchUrl).build()
        val htmlResponse = okHttpClient.newCall(searchRequest).execute().body!!.string()
        val shotElements = Jsoup.parse(htmlResponse, dribbbleUrl).select("li[id^=screenshot]")
        val shots: MutableList<Shot> = ArrayList(shotElements.size)
        for (element in shotElements) {
            val shot = parseShot(element, DATE_FORMAT)
            if (shot != null) {
                shots.add(shot)
            }
        }
        return shots
    }

    private fun parseShot(element: Element, dateFormat: SimpleDateFormat): Shot {
        val descriptionBlock = element.select("a.dribbble-over").first()
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
            createdAt = dateFormat.parse(descriptionBlock.select("em.timestamp").first().text())
        } catch (e: ParseException) {
        }
        Log.d(Constants.TAG, "search: $imgUrl")
        return Shot.Builder()
            .setId(element.id().replace("screenshot-", "").toLong())
            .setHtmlUrl(dribbbleUrl + element.select("a.dribbble-link").first().attr("href"))
            .setTitle(descriptionBlock.select("strong").first().text())
            .setDescription(description)
            .setImages(Images(null, null, imgUrl))
            .setAnimated(element.select("div.gif-indicator").first() != null)
            .setCreatedAt(createdAt)
            .setLikesCount(
                element.select("li.fav").first().child(0).text().replace(",".toRegex(), "").toInt()
            )
            .setCommentsCount(
                element.select("li.cmnt").first().child(0).text().replace(",".toRegex(), "").toInt()
            )
            .setViewsCount(
                element.select("li.views").first().child(0).text().replace(",".toRegex(), "")
                    .toInt()
            )
            .setUser(parseUser(element.select("h2").first()))
            .build()
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
        return User.Builder()
            .setId(id)
            .setName(userBlock.text())
            .setUsername(username)
            .setHtmlUrl(dribbbleUrl + slashUsername)
            .setAvatarUrl(avatarUrl)
            .setPro(element.select("span.badge-pro").size > 0)
            .build()
    }

    companion object {
        private val PATTERN_PLAYER_ID = Pattern.compile("users/(\\d+?)/", Pattern.DOTALL)
        private val DATE_FORMAT = SimpleDateFormat("MMMM d, yyyy")
    }
}