package com.bubbble.data.users

import com.bubbble.core.models.user.User
import com.bubbble.core.network.DribbbleApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUser(userName: String): User {
        TODO()
        //return dribbbleApi.getUser(userName)
    }

}