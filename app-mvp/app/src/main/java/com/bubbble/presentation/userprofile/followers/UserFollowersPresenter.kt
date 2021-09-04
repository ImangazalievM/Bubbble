package com.bubbble.presentation.userprofile.followers

import com.arellomobile.mvp.InjectViewState
import com.bubbble.core.models.user.Follow
import com.bubbble.core.models.user.UserFollowersParams
import com.bubbble.core.network.exceptions.NoNetworkException
import com.bubbble.domain.userprofile.UserFollowersInteractor
import com.bubbble.presentation.global.mvp.BasePresenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@InjectViewState
class UserFollowersPresenter @AssistedInject constructor(
    private val userFollowersInteractor: UserFollowersInteractor,
    @Assisted private val userId: Long
) : BasePresenter<UserFollowersView>() {

    private var currentMaxPage = 1
    private val followers: MutableList<Follow> = ArrayList()
    private var isFollowersLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showFollowersLoadingProgress()
        loadMoreFollowers(currentMaxPage)
    }

    private fun loadMoreFollowers(page: Int) = launchSafe {
        isFollowersLoading = true
        val requestParams = UserFollowersParams(userId, page, PAGE_SIZE)
        try {
            val newFollowers = userFollowersInteractor.getUserFollowers(requestParams)
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
        viewState.openUserDetailsScreen(followers[position].follower.id)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Long): UserFollowersPresenter
    }

}