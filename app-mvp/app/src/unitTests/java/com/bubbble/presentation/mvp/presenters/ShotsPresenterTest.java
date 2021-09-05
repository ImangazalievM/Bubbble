package com.bubbble.presentation.mvp.presenters;

import com.bubbble.domain.global.exceptions.NoNetworkException;
import com.bubbble.shots.shotslist.ShotsInteractor;
import com.bubbble.domain.global.models.Shot;
import com.bubbble.domain.global.models.ShotsRequestParams;
import com.bubbble.shots.shotslist.ShotsPresenter;
import com.bubbble.shots.shotslist.ShotsView;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class ShotsPresenterTest {

    private static final String TEST_SORT_TYPE = "sort_type";

    @Mock
    private ShotsInteractor interactor;
    @Mock
    private ShotsView view;
    private ShotsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShotsPresenter(interactor, new TestSchedulersProvider(), TEST_SORT_TYPE);
    }

    @Test
    public void shots_shouldLoadAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        presenter.attachView(view);

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(interactor).getShots(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        verify(view).showNewShots(shots);
    }

    @Test
    public void onLoadMoreShotsRequest_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(interactor, times(3)).getShots(shotsCaptor.capture());
        List<ShotsRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(view, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).getShots(any(ShotsRequestParams.class));
        verify(view).hideShotsLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).getShots(any(ShotsRequestParams.class));
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.retryLoading();

        // assert
        verify(interactor, times(3)).getShots(any(ShotsRequestParams.class));
        verify(view, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor, times(1)).getShots(any(ShotsRequestParams.class));
        verify(view).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();

        // assert
        verify(interactor, times(2)).getShots(any(ShotsRequestParams.class));
        verify(view).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.getShots(any(ShotsRequestParams.class)))
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
