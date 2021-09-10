package com.bubbble.presentation.userprofile

import com.arellomobile.mvp.InjectViewState
import com.bubbble.core.models.user.User
import com.bubbble.core.network.NoNetworkException
import com.bubbble.domain.userprofile.UserProfileInteractor
import com.bubbble.coreui.mvp.BasePresenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class UserProfilePresenter @AssistedInject constructor(
    private val userProfileInteractor: UserProfileInteractor,
    @Assisted private val userId: Long
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
        } catch (throwable: com.bubbble.core.network.NoNetworkException) {
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

    @AssistedFactory
    interface Factory {
        fun create(userId: Long): UserProfilePresenter
    }

}