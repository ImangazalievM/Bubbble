package com.bubbble.data.users

import com.bubbble.core.DribbbleApi
import com.bubbble.models.User
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val dribbbleApi: com.bubbble.core.DribbbleApi
) {

    suspend fun getUser(userId: Long): com.bubbble.models.User {
        return dribbbleApi.getUser(userId)
    }

}