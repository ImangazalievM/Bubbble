package com.bubbble.userprofile

import moxy.InjectViewState
import com.bubbble.core.models.user.User
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.global.UserUrlParser
import com.bubbble.data.users.UsersRepository
import com.bubbble.userprofile.api.UserProfileScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class UserProfilePresenter @AssistedInject constructor(
    private val usersRepository: UsersRepository,
    private val userUrlParser: UserUrlParser,
    @Assisted private val userName: String
) : BasePresenter<UserProfileView>() {

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
            viewState.showUser(user)
        } catch (throwable: NoNetworkException) {
            viewState.showNoNetworkLayout(true)
        } finally {
            viewState.showLoadingProgress(false)
        }
    }

    fun retryLoading() {
        viewState.showNoNetworkLayout(false)
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

    fun onUserSelected(userUrl: String) {
        val selectedUserName = userUrlParser.getUserName(userUrl)
        if (selectedUserName != null) {
            router.navigateTo(UserProfileScreen(selectedUserName))
        } else {
            //ToDo: handle it
        }
    }

    override fun onBackPressed() {
        viewState.closeScreen()
    }

    @AssistedFactory
    interface Factory {
        fun create(userName: String): UserProfilePresenter
    }

}