package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserProfileInteractor;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.views.UserProfileView;
import com.imangazalievm.bubbble.presentation.mvp.views.UserProfileView$$State;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;
import com.imangazalievm.bubbble.test.TestRxSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class UserProfilePresenterTest {

    private static final long TEST_USER_ID = 5864664L;
    private static final String TEST_USER_URL = "https://test-url.com/users/9651";

    @Mock
    UserProfileInteractor userProfileInteractorMock;
    @Mock
    UserProfileView userProfileViewMock;
    @Mock
    UserProfileView$$State userProfileViewStateMock;

    private UserProfilePresenter userProfilePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userProfilePresenter = new UserProfilePresenter(userProfileInteractorMock, new TestRxSchedulerProvider(), TEST_USER_ID);
        userProfilePresenter.setViewState(userProfileViewStateMock);
    }

    @Test
    public void user_shouldLoadAndShowUserInfoOnFirstAttach() {
        //arrange
        User user = new User();
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userProfilePresenter.onFirstViewAttach();

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).showLoadingProgress();
        verify(userProfileViewStateMock).showUser(user);
    }

    @Test
    public void shot_shouldShowNoNetworkLayout() {
        //arrange
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userProfilePresenter.onFirstViewAttach();

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).hideLoadingProgress();
        verify(userProfileViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadUserShowProgress() {
        //arrange
        User user = new User();
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(user));

        // act
        userProfilePresenter.onFirstViewAttach();
        userProfilePresenter.retryLoading();

        // assert
        verify(userProfileInteractorMock, times(2)).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).hideNoNetworkLayout();
        verify(userProfileViewStateMock, times(2)).showLoadingProgress();
    }

    @Test
    public void onShareProfileClicked_shouldShowProfileSharing() {
        //arrange
        User user = new User();
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userProfilePresenter.onFirstViewAttach();
        userProfilePresenter.onShareProfileClicked();

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).showUserProfileSharing(user);
    }

    @Test
    public void onOpenInBrowserClicked_shouldOpenUserProfileInBrowser() {
        //arrange
        User user = mock(User.class);
        when(user.getHtmlUrl()).thenReturn(TEST_USER_URL);
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userProfilePresenter.onFirstViewAttach();
        userProfilePresenter.onOpenInBrowserClicked();

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).openInBrowser(TEST_USER_URL);
    }

    @Test
    public void onLinkClicked_shouldOpenLinkInBrowser() {
        //arrange
        User user = new User();
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userProfilePresenter.onFirstViewAttach();
        userProfilePresenter.onLinkClicked(TEST_USER_URL);

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).openInBrowser(TEST_USER_URL);
    }

    @Test
    public void onUserSelected_shouldOpenUserProfileScreen() {
        //arrange
        long anotherUserId = 1628256L;
        User user = new User();
        when(userProfileInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userProfilePresenter.onFirstViewAttach();
        userProfilePresenter.onUserSelected(anotherUserId);

        // assert
        verify(userProfileInteractorMock).getUser(TEST_USER_ID);
        verify(userProfileViewStateMock).openUserProfileScreen(anotherUserId);
    }

}
