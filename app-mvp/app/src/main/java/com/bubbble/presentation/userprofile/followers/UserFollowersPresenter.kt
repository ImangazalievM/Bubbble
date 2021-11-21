package com.bubbble.presentation.userprofile.followers

import com.bubbble.core.models.user.Follow
import com.bubbble.core.models.user.UserFollowersParams
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.users.FollowersRepository
import com.bubbble.presentation.global.navigation.UserProfileScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import moxy.InjectViewState
import java.util.*

@InjectViewState
class UserFollowersPresenter @AssistedInject constructor(
    private val followersRepository: FollowersRepository,
    @Assisted private val userName: String
) : BasePresenter<UserFollowersView>() {

    private var currentMaxPage = 1
    private val followers: MutableList<Follow> = ArrayList()
    private var isFollowersLoading = false

    override fun onFirstViewAttach() {
        viewState.showFollowersLoadingProgress()
        loadMoreFollowers(currentMaxPage)
    }

    private fun loadMoreFollowers(page: Int) = launchSafe {
        isFollowersLoading = true
        val requestParams = UserFollowersParams(userName, page, PAGE_SIZE)
        try {
            val newFollowers = followersRepository.getUserFollowers(requestParams)
            followers.addAll(newFollowers)
            viewState.showNewFollowers(newFollowers)
        } catch (throwable: NoNetworkException) {
            if (isFirstLoading) {
                viewState.showNoNetworkLayout()
            } else {
                viewState.showLoadMoreError()
            }
        } finally {
            viewState.hideFollowersLoadingProgress()
            isFollowersLoading = false
        }
    }

    private val isFirstLoading: Boolean
        private get() = currentMaxPage == 1

    fun onLoadMoreFollowersRequest() {
        if (isFollowersLoading) {
            return
        }
        viewState.showFollowersLoadingMoreProgress()
        currentMaxPage++
        loadMoreFollowers(currentMaxPage)
    }

    fun retryLoading() {
        if (isFirstLoading) {
            viewState.hideNoNetworkLayout()
            viewState.showFollowersLoadingProgress()
        } else {
            viewState.showFollowersLoadingMoreProgress()
        }
        loadMoreFollowers(currentMaxPage)
    }

    fun onFollowerClick(position: Int) {
        println(followers.size)
        println(followers[position])
        println(followers[position].follower)
        println(followers[position].follower.id)
        router.navigateTo(UserProfileScreen(followers[position].follower.userName))
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    @AssistedFactory
    interface Factory {
        fun create(userName: String): UserFollowersPresenter
    }

}