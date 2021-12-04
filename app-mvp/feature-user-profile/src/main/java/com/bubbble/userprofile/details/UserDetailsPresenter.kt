package com.bubbble.userprofile.details

import moxy.InjectViewState
import com.bubbble.core.models.user.User
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.users.UsersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class UserDetailsPresenter @AssistedInject constructor(
    private val usersRepository: UsersRepository,
    @Assisted  private val userName: String
) : BasePresenter<UserDetailsView>() {

    private lateinit var user: User
    private val isUserLoaded: Boolean
        get() = ::user.isInitialized

    override fun onFirstViewAttach() {
        loadUser()
    }

    private fun loadUser() = launchSafe {
        viewState.showLoadingProgress(true)
        try {
            user = usersRepository.getUser(userName)
            viewState.showUserInfo(user)
        } catch (e: NoNetworkException) {
            viewState.showNoNetworkLayout(true)
        } finally {
            viewState.showLoadingProgress(false)
        }
    }

    fun retryLoading() {
        viewState.showNoNetworkLayout(false)
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
        fun create(userName: String): UserDetailsPresenter
    }
}