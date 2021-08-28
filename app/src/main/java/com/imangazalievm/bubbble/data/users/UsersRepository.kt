package com.imangazalievm.bubbble.data.users

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService
import com.imangazalievm.bubbble.domain.global.models.User
import io.reactivex.Single
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val dribbbleApiService: DribbbleApiService
) {
    
    fun getUser(userId: Long): Single<User> {
        return dribbbleApiService.getUser(userId)
    }

}