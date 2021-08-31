package com.bubbble.presentation.mvp.presenters;

import com.bubbble.domain.global.exceptions.NoNetworkException;
import com.bubbble.domain.userprofile.UserFollowersInteractor;
import com.bubbble.domain.global.models.Follow;
import com.bubbble.domain.global.models.User;
import com.bubbble.domain.global.models.UserFollowersRequestParams;
import com.bubbble.presentation.userprofile.followers.UserFollowersPresenter;
import com.bubbble.presentation.userprofile.followers.UserFollowersView;
import com.bubbble.test.BubbbleTestRunner;
import com.bubbble.test.TestSchedulersProvider;

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
    private UserFollowersInteractor interactor;
    @Mock
    private UserFollowersView view;
    private UserFollowersPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserFollowersPresenter(interactor, new TestSchedulersProvider(), TEST_USER_ID);
    }

    @Test
    public void followers_shouldLoadAndShowUserFollowersOnFirstAttach() {
        //arrange
        List<Follow> follows = follows();
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class))).thenReturn(Single.just(follows));

        //act
        presenter.attachView(view);

        // assert
        ArgumentCaptor<UserFollowersRequestParams> followersCaptor = ArgumentCaptor.forClass(UserFollowersRequestParams.class);
        verify(interactor).getUserFollowers(followersCaptor.capture());
        assertEquals(1, followersCaptor.getValue().getPage());
        verify(view).showNewFollowers(follows);
    }

    @Test
    public void onLoadMoreFollowersRequest_shouldCorrectLoadNextFollowers() {
        //arrange
        List<Follow> follows = follows();
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreFollowersRequest();
        presenter.onLoadMoreFollowersRequest();

        // assert
        ArgumentCaptor<UserFollowersRequestParams> followersCaptor = ArgumentCaptor.forClass(UserFollowersRequestParams.class);
        verify(interactor, times(3)).getUserFollowers(followersCaptor.capture());
        List<UserFollowersRequestParams> capturedsRequestParams = followersCaptor.getAllValues();
        assertEquals(1, capturedsRequestParams.get(0).getPage());
        assertEquals(2, capturedsRequestParams.get(1).getPage());
        assertEquals(3, capturedsRequestParams.get(2).getPage());
        verify(view, times(3)).showNewFollowers(follows);
    }

    @Test
    public void followers_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(view).hideFollowersLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Follow> follows = follows();
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(follows));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showFollowersLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Follow> follows = follows();
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreFollowersRequest();
        presenter.retryLoading();

        // assert
        verify(interactor, times(3)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(view, times(2)).showFollowersLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor, times(1)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(view).hideFollowersLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Follow> follows = follows();
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreFollowersRequest();

        // assert
        verify(interactor, times(2)).getUserFollowers(any(UserFollowersRequestParams.class));
        verify(view).hideFollowersLoadingMoreProgress();
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
        when(interactor.getUserFollowers(any(UserFollowersRequestParams.class)))
                .thenReturn(Single.just(follows));

        // act
        presenter.attachView(view);
        presenter.onFollowerClick(2);

        // assert
        verify(view).openUserDetailsScreen(TEST_USER_ID);
    }

    private List<Follow> follows() {
        List<Follow> follows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            follows.add(new Follow());
        }
        return follows;
    }

}
