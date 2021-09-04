package com.bubbble.presentation.mvp.presenters;

import com.bubbble.domain.global.exceptions.NoNetworkException;
import com.bubbble.domain.userprofile.UserProfileInteractor;
import com.bubbble.domain.global.models.User;
import com.bubbble.presentation.userprofile.UserProfilePresenter;
import com.bubbble.presentation.userprofile.UserProfileView;
import com.bubbble.test.BubbbleTestRunner;
import com.bubbble.test.TestSchedulersProvider;

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
    private UserProfileInteractor interactor;
    @Mock
    private UserProfileView view;
    private UserProfilePresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserProfilePresenter(interactor, new TestSchedulersProvider(), TEST_USER_ID);
    }

    @Test
    public void user_shouldLoadAndShowUserInfoOnFirstAttach() {
        //arrange
        User user = new User();
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).showLoadingProgress();
        verify(view).showUser(user);
    }

    @Test
    public void shot_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).hideLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadUserShowProgress() {
        //arrange
        User user = new User();
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(user));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).getUser(TEST_USER_ID);
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showLoadingProgress();
    }

    @Test
    public void onShareProfileClicked_shouldShowProfileSharing() {
        //arrange
        User user = new User();
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onShareProfileClicked();

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).showUserProfileSharing(user);
    }

    @Test
    public void onOpenInBrowserClicked_shouldOpenUserProfileInBrowser() {
        //arrange
        User user = mock(User.class);
        when(user.getHtmlUrl()).thenReturn(TEST_USER_URL);
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onOpenInBrowserClicked();

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).openInBrowser(TEST_USER_URL);
    }

    @Test
    public void onLinkClicked_shouldOpenLinkInBrowser() {
        //arrange
        User user = new User();
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onLinkClicked(TEST_USER_URL);

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).openInBrowser(TEST_USER_URL);
    }

    @Test
    public void onUserSelected_shouldOpenUserProfileScreen() {
        //arrange
        long anotherUserId = 1628256L;
        User user = new User();
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onUserSelected(anotherUserId);

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).openUserProfileScreen(anotherUserId);
    }

}
