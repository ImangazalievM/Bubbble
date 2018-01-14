package com.imangazalievm.bubbble.data.repository.datasource;

import android.text.TextUtils;
import android.util.Log;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.domain.global.models.Images;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DribbbleSearchDataSource {

    private static final Pattern PATTERN_PLAYER_ID = Pattern.compile("users/(\\d+?)/", Pattern.DOTALL);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    private OkHttpClient okHttpClient;
    private String dribbbleUrl;

    @Inject
    public DribbbleSearchDataSource(OkHttpClient okHttpClient, String dribbbleUrl) {
        this.okHttpClient = okHttpClient;
        this.dribbbleUrl = dribbbleUrl;
    }

    public Single<List<Shot>> search(String query, String sort, Integer page, Integer pageSize) {
        return Single.fromCallable(() -> {
            HttpUrl searchUrl = HttpUrl.parse(dribbbleUrl + "/search")
                    .newBuilder()
                    .addQueryParameter("q", query)
                    .addQueryParameter("s", sort)
                    .addQueryParameter("page", String.valueOf(page))
                    .addQueryParameter("per_page", String.valueOf(pageSize))
                    .build();
            Request searchRequest = new Request.Builder().url(searchUrl).build();
            String htmlResponse = okHttpClient.newCall(searchRequest).execute().body().string();
            Elements shotElements = Jsoup.parse(htmlResponse, dribbbleUrl).select("li[id^=screenshot]");

            final List<Shot> shots = new ArrayList<>(shotElements.size());
            for (Element element : shotElements) {
                final Shot shot = parseShot(element, DATE_FORMAT);
                if (shot != null) {
                    shots.add(shot);
                }
            }
            return shots;
        });
    }

    private Shot parseShot(Element element, SimpleDateFormat dateFormat) {
        final Element descriptionBlock = element.select("a.dribbble-over").first();
        // API responses wrap description in a <p> tag. Do the same for consistent display.
        String description = descriptionBlock.select("span.comment").text().trim();
        if (!TextUtils.isEmpty(description)) {
            description = "<p>" + description + "</p>";
        }
        String imgUrl = element.select("img").first().attr("src");
        if (imgUrl.contains("_teaser.")) {
            imgUrl = imgUrl.replace("_teaser.", ".");
        }
        Date createdAt = null;
        try {
            createdAt = dateFormat.parse(descriptionBlock.select("em.timestamp").first().text());
        } catch (ParseException e) { }

        Log.d(Constants.TAG, "search: " + imgUrl);
        return new Shot.Builder()
                .setId(Long.parseLong(element.id().replace("screenshot-", "")))
                .setHtmlUrl(dribbbleUrl + element.select("a.dribbble-link").first().attr("href"))
                .setTitle(descriptionBlock.select("strong").first().text())
                .setDescription(description)
                .setImages(new Images(null, null, imgUrl))
                .setAnimated(element.select("div.gif-indicator").first() != null)
                .setCreatedAt(createdAt)
                .setLikesCount(Integer.parseInt(element.select("li.fav").first().child(0).text().replaceAll(",", "")))
                .setCommentsCount(Integer.parseInt(element.select("li.cmnt").first().child(0).text().replaceAll(",", "")))
                .setViewsCount(Integer.parseInt(element.select("li.views").first().child(0).text().replaceAll(",", "")))
                .setUser(parseUser(element.select("h2").first()))
                .build();
    }

    private User parseUser(Element element) {
        final Element userBlock = element.select("a.url").first();
        String avatarUrl = userBlock.select("img.photo").first().attr("src");
        if (avatarUrl.contains("/mini/")) {
            avatarUrl = avatarUrl.replace("/mini/", "/normal/");
        }
        final Matcher matchId = PATTERN_PLAYER_ID.matcher(avatarUrl);
        Long id = -1l;
        if (matchId.find() && matchId.groupCount() == 1) {
            id = Long.parseLong(matchId.group(1));
        }
        final String slashUsername = userBlock.attr("href");
        final String username =
                TextUtils.isEmpty(slashUsername) ? null : slashUsername.substring(1);
        return new User.Builder()
                .setId(id)
                .setName(userBlock.text())
                .setUsername(username)
                .setHtmlUrl(dribbbleUrl + slashUsername)
                .setAvatarUrl(avatarUrl)
                .setPro(element.select("span.badge-pro").size() > 0)
                .build();
    }

}
