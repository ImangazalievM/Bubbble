package com.bubbble.presentation.userprofile.details

import com.arellomobile.mvp.InjectViewState
import com.bubbble.data.global.network.exceptions.NoNetworkException
import com.bubbble.domain.global.models.User
import com.bubbble.domain.userprofile.UserDetailsInteractor
import com.bubbble.presentation.global.mvp.BasePresenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class UserDetailsPresenter @AssistedInject constructor(
    private val userDetailsInteractor: UserDetailsInteractor,
    @Assisted  private val userId: Long
) : BasePresenter<UserDetailsView>() {

    private lateinit var user: User
    private val isUserLoaded: Boolean
        get() = ::user.isInitialized

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() = launchSafe {
        viewState.showLoadingProgress()
        try {
            this@UserDetailsPresenter.user = userDetailsInteractor.getUser(userId)
            viewState.showUserInfo(user)
        } catch (e: NoNetworkException) {
            viewState.showNoNetworkLayout()
        } finally {
            viewState.hideLoadingProgress()
        }
    }

    fun retryLoading() {
        viewState.hideNoNetworkLayout()
        loadUser()
    }

    fun onUserTwitterButtonClicked() {
        if (!isUserLoaded) return
        viewState.openInBrowser(user.links.twitter)
    }

    fun onUserWebsiteButtonClicked() {
        if (!isUserLoaded) return
        viewState.openInBrowser(user.links.web)
    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Long): UserDetailsPresenter
    }

}