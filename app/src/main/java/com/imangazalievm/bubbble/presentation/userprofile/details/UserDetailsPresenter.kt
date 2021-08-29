package com.imangazalievm.bubbble.presentation.userprofile.details

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.data.global.network.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.domain.userprofile.UserDetailsInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class UserDetailsPresenter @Inject constructor(
    private val userDetailsInteractor: UserDetailsInteractor,
    private val userId: Long
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
}