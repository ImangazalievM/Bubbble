package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotsSearchInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsSearchView;
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
public class ShotsSearchPresenterTest {

    private static final String TEST_SEARCH_REQUEST = "Test search request";

    @Mock
    private ShotsSearchInteractor interactor;
    @Mock
    private ShotsSearchView view;
    private ShotsSearchPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShotsSearchPresenter(interactor, new TestRxSchedulerProvider(), TEST_SEARCH_REQUEST);
    }

    @Test
    public void shots_shouldSearchAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        presenter.attachView(view);

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(interactor).search(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        assertEquals(TEST_SEARCH_REQUEST, shotsCaptor.getValue().getSearchQuery());
        verify(view).showNewShots(shots);
    }

    @Test
    public void onSearchQuery_shouldSearchAndShowShots() {
        //arrange
        String newSearchQuery = "Search request";

        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        presenter.attachView(view);
        presenter.onNewSearchQuery(newSearchQuery);

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(interactor, times(2)).search(shotsCaptor.capture());
        List<ShotsSearchRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(TEST_SEARCH_REQUEST, capturedRequestParams.get(0).getSearchQuery());
        assertEquals(newSearchQuery, capturedRequestParams.get(1).getSearchQuery());

        verify(view).clearShotsList();
        verify(view, times(2)).showNewShots(shots);
    }

    @Test
    public void onLoadMoreShotsRequest_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(interactor, times(3)).search(shotsCaptor.capture());
        List<ShotsSearchRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(view, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).search(any(ShotsSearchRequestParams.class));
        verify(view).hideShotsLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).search(any(ShotsSearchRequestParams.class));
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();
        presenter.retryLoading();

        // assert
        verify(interactor, times(3)).search(any(ShotsSearchRequestParams.class));
        verify(view, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor, times(1)).search(any(ShotsSearchRequestParams.class));
        verify(view).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreShotsRequest();

        // assert
        verify(interactor, times(2)).search(any(ShotsSearchRequestParams.class));
        verify(view).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(interactor.search(any(ShotsSearchRequestParams.class)))
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
