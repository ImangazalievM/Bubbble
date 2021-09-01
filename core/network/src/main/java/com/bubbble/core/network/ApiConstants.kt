package com.bubbble.core.network

object ApiConstants {

    const val DRIBBBLE_API_URL = "https://api.dribbble.com/v2/"

    object Authorize {
        const val path = "oauth/authorize"

        const val p_client_id = "client_id"
        const val p_redirect_uri = "redirect_uri"
        const val p_scope = "scope"
        const val p_state = "state"
    }

    object Token {
        const val path = "oauth/token"

        const val p_client_id = "client_id"
        const val p_client_secret = "client_secret"
        const val p_code = "code"
        const val p_redirect_uri = "redirect_uri"
    }

    object Shots {
        const val path = "user/shots"

        const val p_image = "image "
        const val p_title = "title "
        const val p_description = "description "
        const val p_low_profile = "low_profile "
        const val p_rebound_source_id = "rebound_source_id"
        const val p_scheduled_for = "scheduled_for"
        const val p_tags = "tags "
        const val p_team_id = "team_id"
    }

    object Projects {
        const val path = "user/projects"

        const val p_name = "name"
        const val p_description = "description"
    }

}