package com.bubbble.data.users

import com.bubbble.data.global.network.DribbbleApi
import com.bubbble.domain.global.models.User
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUser(userId: Long): User {
        return dribbbleApi.getUser(userId)
    }

}