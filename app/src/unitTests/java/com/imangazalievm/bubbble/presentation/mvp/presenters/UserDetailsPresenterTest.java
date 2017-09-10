package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.Links;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.views.UserDetailsView;
import com.imangazalievm.bubbble.presentation.mvp.views.UserDetailsView$$State;
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
    UserDetailsInteractor userDetailsInteractorMock;
    @Mock
    UserDetailsView userDetailsViewMock;
    @Mock
    UserDetailsView$$State userDetailsViewStateMock;

    private UserDetailsPresenter userDetailsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDetailsPresenter = new UserDetailsPresenter(userDetailsInteractorMock, new TestRxSchedulerProvider(), TEST_USER_ID);
        userDetailsPresenter.setViewState(userDetailsViewStateMock);
    }

    @Test
    public void user_shouldLoadAndShowUserInfoOnFirstAttach() {
        //arrange
        User user = new User();
        when(userDetailsInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userDetailsPresenter.onFirstViewAttach();

        // assert
        verify(userDetailsInteractorMock).getUser(TEST_USER_ID);
        verify(userDetailsViewStateMock).showLoadingProgress();
        verify(userDetailsViewStateMock).showUserInfo(user);
    }

    @Test
    public void shot_shouldShowNoNetworkLayout() {
        //arrange
        when(userDetailsInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userDetailsPresenter.onFirstViewAttach();

        // assert
        verify(userDetailsInteractorMock).getUser(TEST_USER_ID);
        verify(userDetailsViewStateMock).hideLoadingProgress();
        verify(userDetailsViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadUserShowProgress() {
        //arrange
        User user = new User();
        when(userDetailsInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(user));

        // act
        userDetailsPresenter.onFirstViewAttach();
        userDetailsPresenter.retryLoading();

        // assert
        verify(userDetailsInteractorMock, times(2)).getUser(TEST_USER_ID);
        verify(userDetailsViewStateMock).hideNoNetworkLayout();
        verify(userDetailsViewStateMock, times(2)).showLoadingProgress();
    }

    @Test
    public void onUserTwitterButtonClicked_shouldOpenUserTwitterAccountInBrowser() {
        //arrange
        String testTwitterAccountLink = "https://twitter.com/dribbble";
        Links links = mock(Links.class);
        when(links.getTwitter()).thenReturn(testTwitterAccountLink);
        User user = mock(User.class);
        when(user.getLinks()).thenReturn(links);
        when(userDetailsInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userDetailsPresenter.onFirstViewAttach();
        userDetailsPresenter.onUserTwitterButtonClicked();

        // assert
        verify(userDetailsInteractorMock).getUser(TEST_USER_ID);
        verify(userDetailsViewStateMock).openInBrowser(testTwitterAccountLink);
    }

    @Test
    public void onUserWebsiteButtonClicked_shouldOpenUserWebSiteInBrowser() {
        //arrange
        String testUserWebSiteUrl = "https://my-site.com";
        Links links = mock(Links.class);
        when(links.getWeb()).thenReturn(testUserWebSiteUrl);
        User user = mock(User.class);
        when(user.getLinks()).thenReturn(links);
        when(userDetailsInteractorMock.getUser(TEST_USER_ID))
                .thenReturn(Single.just(user));

        //act
        userDetailsPresenter.onFirstViewAttach();
        userDetailsPresenter.onUserWebsiteButtonClicked();

        // assert
        verify(userDetailsInteractorMock).getUser(TEST_USER_ID);
        verify(userDetailsViewStateMock).openInBrowser(testUserWebSiteUrl);
    }

}
