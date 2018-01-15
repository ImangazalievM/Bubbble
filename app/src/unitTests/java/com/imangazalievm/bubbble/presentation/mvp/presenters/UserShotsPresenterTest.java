package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.userprofile.UserShotsInteractor;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserShotsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserShotsView;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;
import com.imangazalievm.bubbble.test.TestSchedulersProvider;

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
    private UserShotsInteractor interactor;
    @Mock
    private UserShotsView view;
    private UserShotsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserShotsPresenter(interactor, new TestSchedulersProvider(), TEST_USER_ID);
    }

    @Test
    public void shots_shouldLoadAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        presenter.attachView(view);

        // assert
        ArgumentCaptor<UserShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(UserShotsRequestParams.class);
        verify(interactor).getUserShots(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        verify(view).showShotsLoadingProgress();
        verify(view).showNewShots(shots);
    }

    @Test
    public void shots_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<UserShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(UserShotsRequestParams.class);
        verify(interactor, times(3)).getUserShots(shotsCaptor.capture());
        List<UserShotsRequestParams> capturedsRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedsRequestParams.get(0).getPage());
        assertEquals(2, capturedsRequestParams.get(1).getPage());
        assertEquals(3, capturedsRequestParams.get(2).getPage());
        verify(view, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).getUserShots(any(UserShotsRequestParams.class));
        verify(view).hideShotsLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).getUserShots(any(UserShotsRequestParams.class));
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.retryLoading();

        // assert
        verify(interactor, times(3)).getUserShots(any(UserShotsRequestParams.class));
        verify(view, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor, times(1)).getUserShots(any(UserShotsRequestParams.class));
        verify(view).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();

        // assert
        verify(interactor, times(2)).getUserShots(any(UserShotsRequestParams.class));
        verify(view).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getUserShots(any(UserShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        // act
        presenter.attachView(view);
        presenter.onShotClick(2);

        // assert
        verify(view).openShotDetailsScreen(shots.get(2).getId());
    }

    private List<Shot> shots() {
        List<Shot> repositories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            repositories.add(new Shot());
        }
        return repositories;
    }

}
