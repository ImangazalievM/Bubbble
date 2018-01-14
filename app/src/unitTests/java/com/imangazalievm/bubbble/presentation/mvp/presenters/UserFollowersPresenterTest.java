package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserFollowersInteractor;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.UserFollowersView;
import com.imangazalievm.bubbble.presentation.mvp.views.UserFollowersView$$State;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;
import com.imangazalievm.bubbble.test.TestRxSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class UserFollowersPresenterTest {

    private static final long TEST_USER_ID = 5864664L;

    @Mock
    UserFollowersInteractor userFollowersInteractorMock;
    @Mock
    UserFollowersView userFollowersViewMock;

    private UserFollowersPresenter userFollowersPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userFollowersPresenter = new UserFollowersPresenter(userFollowersInteractorMock, new TestRxSchedulerProvider(), TEST_USER_ID);
    }

    @Test
    public void followers_shouldLoadAndShowUserFollowersOnFirstAttach() {
        //arrange
        List<Follow> follows = follows();
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class))).thenReturn(Single.just(follows));

        //act
        userFollowersPresenter.attachView(userFollowersViewMock);

        // assert
        ArgumentCaptor<UserFollowersRequestParams> followersCaptor = ArgumentCaptor.forClass(UserFollowersRequestParams.class);
        verify(userFollowersInteractorMock).getUserFollowers(followersCaptor.capture());
        assertEquals(1, followersCaptor.getValue().getPage());
        verify(userFollowersViewMock).showNewFollowers(follows);
    }

    @Test
    public void onLoadMoreFollowersRequest_shouldCorrectLoadNextFollowers() {
        //arrange
        List<Follow> follows = follows();
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);
        userFollowersPresenter.onLoadMoreFollowersRequest();
        userFollowersPresenter.onLoadMoreFollowersRequest();

        // assert
        ArgumentCaptor<UserFollowersRequestParams> followersCaptor = ArgumentCaptor.forClass(UserFollowersRequestParams.class);
        verify(userFollowersInteractorMock, times(3)).getUserFollowers(followersCaptor.capture());
        List<UserFollowersRequestParams> capturedsRequestParams = followersCaptor.getAllValues();
        assertEquals(1, capturedsRequestParams.get(0).getPage());
        assertEquals(2, capturedsRequestParams.get(1).getPage());
        assertEquals(3, capturedsRequestParams.get(2).getPage());
        verify(userFollowersViewMock, times(3)).showNewFollowers(follows);
    }

    @Test
    public void followers_shouldShowNoNetworkLayout() {
        //arrange
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);

        // assert
        verify(userFollowersInteractorMock).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(userFollowersViewMock).hideFollowersLoadingProgress();
        verify(userFollowersViewMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Follow> follows = follows();
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(follows));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);
        userFollowersPresenter.retryLoading();

        // assert
        verify(userFollowersInteractorMock, times(2)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(userFollowersViewMock).hideNoNetworkLayout();
        verify(userFollowersViewMock, times(2)).showFollowersLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Follow> follows = follows();
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);
        userFollowersPresenter.onLoadMoreFollowersRequest();
        userFollowersPresenter.retryLoading();

        // assert
        verify(userFollowersInteractorMock, times(3)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(userFollowersViewMock, times(2)).showFollowersLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);

        // assert
        verify(userFollowersInteractorMock, times(1)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(userFollowersViewMock).hideFollowersLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Follow> follows = follows();
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);
        userFollowersPresenter.onLoadMoreFollowersRequest();

        // assert
        verify(userFollowersInteractorMock, times(2)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(userFollowersViewMock).hideFollowersLoadingMoreProgress();
    }

    @Test
    public void onFollowerClick_shouldOpenUserDetailsScreen() {
        //arrange
        User follower = mock(User.class);
        when(follower.getId()).thenReturn(TEST_USER_ID);
        Follow follow = mock(Follow.class);
        when(follow.getFollower()).thenReturn(follower);
        List<Follow> follows = follows();
        follows.set(2, follow);
        when(userFollowersInteractorMock.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows));

        // act
        userFollowersPresenter.attachView(userFollowersViewMock);
        userFollowersPresenter.onFollowerClick(2);

        // assert
        verify(userFollowersViewMock).openUserDetailsScreen(TEST_USER_ID);
    }

    private List<Follow> follows() {
        List<Follow> follows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            follows.add(new Follow());
        }
        return follows;
    }

}
