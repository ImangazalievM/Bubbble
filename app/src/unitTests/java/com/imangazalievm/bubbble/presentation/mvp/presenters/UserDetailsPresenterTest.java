package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.userprofile.UserDetailsInteractor;
import com.imangazalievm.bubbble.domain.global.models.Links;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserDetailsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserDetailsView;
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
public class UserDetailsPresenterTest {

    private static final long TEST_USER_ID = 5864664L;

    @Mock
    private UserDetailsInteractor interactor;
    @Mock
    private UserDetailsView view;
    private UserDetailsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserDetailsPresenter(interactor, new TestRxSchedulerProvider(), TEST_USER_ID);
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
        verify(view).showUserInfo(user);
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
    public void onUserTwitterButtonClicked_shouldOpenUserTwitterAccountInBrowser() {
        //arrange
        String testTwitterAccountLink = "https://twitter.com/dribbble";
        Links links = mock(Links.class);
        when(links.getTwitter()).thenReturn(testTwitterAccountLink);
        User user = mock(User.class);
        when(user.getLinks()).thenReturn(links);
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onUserTwitterButtonClicked();

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).openInBrowser(testTwitterAccountLink);
    }

    @Test
    public void onUserWebsiteButtonClicked_shouldOpenUserWebSiteInBrowser() {
        //arrange
        String testUserWebSiteUrl = "https://my-site.com";
        Links links = mock(Links.class);
        when(links.getWeb()).thenReturn(testUserWebSiteUrl);
        User user = mock(User.class);
        when(user.getLinks()).thenReturn(links);
        when(interactor.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        presenter.attachView(view);
        presenter.onUserWebsiteButtonClicked();

        // assert
        verify(interactor).getUser(TEST_USER_ID);
        verify(view).openInBrowser(testUserWebSiteUrl);
    }

}
