package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserShotsInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.UserShotsView;
import com.imangazalievm.bubbble.presentation.mvp.views.UserShotsView$$State;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class UserShotsPresenterTest {

    private static final long TEST_USER_ID = 5864664L;

    @Mock
    UserShotsInteractor userShotsInteractorMock;
    @Mock
    UserShotsView userShotsViewMock;
    @Mock
    UserShotsView$$State userShotsViewStateMock;

    private UserShotsPresenter userShotsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userShotsPresenter = new UserShotsPresenter(userShotsInteractorMock, new TestRxSchedulerProvider(), TEST_USER_ID);
        userShotsPresenter.setViewState(userShotsViewStateMock);
    }

    @Test
    public void shots_shouldLoadAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        userShotsPresenter.onFirstViewAttach();

        // assert
        ArgumentCaptor<UserShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(UserShotsRequestParams.class);
        verify(userShotsInteractorMock).getUserShots(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        verify(userShotsViewStateMock).showShotsLoadingProgress();
        verify(userShotsViewStateMock).showNewShots(shots);
    }

    @Test
    public void shots_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        userShotsPresenter.onFirstViewAttach();
        userShotsPresenter.onLoadMoreShotsRequest();
        userShotsPresenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<UserShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(UserShotsRequestParams.class);
        verify(userShotsInteractorMock, times(3)).getUserShots(shotsCaptor.capture());
        List<UserShotsRequestParams> capturedsRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedsRequestParams.get(0).getPage());
        assertEquals(2, capturedsRequestParams.get(1).getPage());
        assertEquals(3, capturedsRequestParams.get(2).getPage());
        verify(userShotsViewStateMock, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userShotsPresenter.onFirstViewAttach();

        // assert
        verify(userShotsInteractorMock).getUserShots(any(UserShotsRequestParams.class));
        verify(userShotsViewStateMock).hideShotsLoadingProgress();
        verify(userShotsViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        userShotsPresenter.onFirstViewAttach();
        userShotsPresenter.retryLoading();

        // assert
        verify(userShotsInteractorMock, times(2)).getUserShots(any(UserShotsRequestParams.class));
        verify(userShotsViewStateMock).hideNoNetworkLayout();
        verify(userShotsViewStateMock, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userShotsPresenter.onFirstViewAttach();
        userShotsPresenter.onLoadMoreShotsRequest();
        userShotsPresenter.retryLoading();

        // assert
        verify(userShotsInteractorMock, times(3)).getUserShots(any(UserShotsRequestParams.class));
        verify(userShotsViewStateMock, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userShotsPresenter.onFirstViewAttach();

        // assert
        verify(userShotsInteractorMock, times(1)).getUserShots(any(UserShotsRequestParams.class));
        verify(userShotsViewStateMock).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        userShotsPresenter.onFirstViewAttach();
        userShotsPresenter.onLoadMoreShotsRequest();

        // assert
        verify(userShotsInteractorMock, times(2)).getUserShots(any(UserShotsRequestParams.class));
        verify(userShotsViewStateMock).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(userShotsInteractorMock.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        // act
        userShotsPresenter.onFirstViewAttach();
        userShotsPresenter.onShotClick(2);

        // assert
        verify(userShotsViewStateMock).openShotDetailsScreen(shots.get(2).getId());
    }

    private List<Shot> shots() {
        List<Shot> repositories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            repositories.add(new Shot());
        }
        return repositories;
    }

}
