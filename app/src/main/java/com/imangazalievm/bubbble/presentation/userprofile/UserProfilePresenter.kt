package com.imangazalievm.bubbble.presentation.userprofile

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.data.global.network.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.domain.userprofile.UserProfileInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class UserProfilePresenter @Inject constructor(
    private val userProfileInteractor: UserProfileInteractor,
    private val userId: Long
) : BasePresenter<UserProfileView>() {

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
            this@UserProfilePresenter.user = userProfileInteractor.getUser(userId)
            viewState.showUser(user)
        } catch (throwable: NoNetworkException) {
            viewState.showNoNetworkLayout()
        } finally {
            viewState.hideLoadingProgress()
        }
    }

    fun retryLoading() {
        viewState.hideNoNetworkLayout()
        loadUser()
    }

    fun onShareProfileClicked() {
        if (!isUserLoaded) return
        viewState.showUserProfileSharing(user)
    }

    fun onOpenInBrowserClicked() {
        if (!isUserLoaded) return
        viewState.openInBrowser(user.htmlUrl)
    }

    fun onLinkClicked(url: String?) {
        viewState.openInBrowser(url!!)
    }

    fun onUserSelected(userId: Long) {
        viewState.openUserProfileScreen(userId)
    }

    override fun onBackPressed() {
        viewState.closeScreen()
    }

}