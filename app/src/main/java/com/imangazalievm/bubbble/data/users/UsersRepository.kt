package com.imangazalievm.bubbble.data.users

import com.imangazalievm.bubbble.data.global.network.DribbbleApi
import com.imangazalievm.bubbble.domain.global.models.User
import io.reactivex.Single
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUser(userId: Long): User {
        return dribbbleApi.getUser(userId)
    }

}