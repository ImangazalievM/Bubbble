package com.bubbble.userprofile.followers

import com.bubbble.core.models.user.Follow
import com.bubbble.core.models.user.UserFollowersParams
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.users.FollowersRepository
import com.bubbble.userprofile.api.UserProfileScreen
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
    private val isFirstLoading: Boolean
        private get() = currentMaxPage == 1


    override fun onFirstViewAttach() {
        viewState.showFollowersLoadingProgress(true)
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
                viewState.showNoNetworkLayout(true)
            } else {
                viewState.showLoadMoreError()
            }
        } finally {
            viewState.showFollowersLoadingProgress(false)
            isFollowersLoading = false
        }
    }

    fun onLoadMoreFollowersRequest() {
        if (isFollowersLoading) {
            return
        }
        viewState.showFollowersLoadingMoreProgress(true)
        currentMaxPage++
        loadMoreFollowers(currentMaxPage)
    }

    fun retryLoading() {
        if (isFirstLoading) {
            viewState.showNoNetworkLayout(false)
            viewState.showFollowersLoadingProgress(true)
        } else {
            viewState.showFollowersLoadingMoreProgress(true)
        }
        loadMoreFollowers(currentMaxPage)
    }

    fun onFollowerClick(follow: Follow) {
        router.navigateTo(UserProfileScreen(follow.follower.userName))
    }

    @AssistedFactory
    interface Factory {
        fun create(userName: String): UserFollowersPresenter
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}