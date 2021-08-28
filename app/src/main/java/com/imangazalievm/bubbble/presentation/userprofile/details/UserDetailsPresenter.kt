package com.imangazalievm.bubbble.presentation.userprofile.details

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.userprofile.UserDetailsInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.arellomobile.mvp.MvpPresenter
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import javax.inject.Inject

@InjectViewState
class UserDetailsPresenter @Inject constructor(
    private val userDetailsInteractor: UserDetailsInteractor,
    private val schedulersProvider: SchedulersProvider,
    private val userId: Long
) : MvpPresenter<UserDetailsView>() {

    private lateinit var user: User
    private val isUserLoaded: Boolean
        get() = ::user.isInitialized

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() {
        viewState.showLoadingProgress()
        userDetailsInteractor.getUser(userId)
            .observeOn(schedulersProvider.ui())
            .subscribe({ user: User -> onUserLoadSuccess(user) }) { throwable: Throwable ->
                onUserLoadError(
                    throwable
                )
            }
    }

    private fun onUserLoadSuccess(user: User) {
        this.user = user
        viewState.hideLoadingProgress()
        viewState.showUserInfo(user)
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

    fun onUserTwitterButtonClicked() {
        if (!isUserLoaded) return
        viewState.openInBrowser(user.links.twitter)
    }

    fun onUserWebsiteButtonClicked() {
        if (!isUserLoaded) return
        viewState.openInBrowser(user.links.web)
    }
}