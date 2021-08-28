package com.imangazalievm.bubbble.presentation.userprofile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.domain.userprofile.UserProfileInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import javax.inject.Inject

@InjectViewState
class UserProfilePresenter @Inject constructor(
    private val userProfileInteractor: UserProfileInteractor,
    private val schedulersProvider: SchedulersProvider,
    private val userId: Long
) : MvpPresenter<UserProfileView>() {
    
    private lateinit var user: User
    private val isUserLoaded: Boolean
        get() = ::user.isInitialized
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() {
        viewState.showLoadingProgress()
        userProfileInteractor.getUser(userId)
            .observeOn(schedulersProvider.ui())
            .subscribe({ user: User -> onUserLoaded(user) }) { throwable: Throwable ->
                onUserLoadError(
                    throwable
                )
            }
    }

    private fun onUserLoaded(user: User) {
        this.user = user
        viewState.hideLoadingProgress()
        viewState.showUser(user)
    }

    private fun onUserLoadError(throwable: Throwable) {
        if (throwable is NoNetworkException) {
            viewState.hideLoadingProgress()
            viewState.showNoNetworkLayout()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
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

    fun onBackPressed() {
        viewState.closeScreen()
    }

}