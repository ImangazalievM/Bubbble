package com.bubbble.core.network

import com.bubbble.core.network.utils.urlBuilder

object Dribbble {

    const val URL = "https://dribbble.com"

    object Shots {
        const val path = "$URL/shots/"

        enum class Type(val code: String) {
            PERSONAL(""),
            POPULAR("popular"),
            RECENT("recent")
        }

        const val p_page = "page"
        const val p_page_size = "per_page"
        const val p_exclude_shot_ids = "exclude_shot_ids"

        object Category {
            const val all = ""
            const val animation = "animation"
            const val branding = "branding"
            const val illustration = "illustration"
            const val mobile = "mobile"
            const val print = "print"
            const val product_design = "product-design"
            const val typography = "typography"
            const val web_design = "web-design"
            //following|popular|goods|recent|animation|branding|illustration|mobile|print|product-design|typography|web-design
        }
    }

    object User {
        const val shots = "shots"
        const val projects = "projects"
        const val collections = "collections"
        const val likes = "likes"
        const val about = "about"

        const val team_members = "members"

        const val type_pro = "pro"
        const val type_team = "team"

        fun profile(userName: String) = "$URL/$userName"

    }

    object Search {
        const val path = "$URL/search/"

        val shots = null
        const val users = "users"
        const val teams = "teams"

        fun search(query: String, type: String?): String {
            val searchUrl = urlBuilder(path)
            type?.let { searchUrl.addPathSegments(it) }
            searchUrl.addPathSegments(query)
            return searchUrl.build().toString()
        }
    }
}