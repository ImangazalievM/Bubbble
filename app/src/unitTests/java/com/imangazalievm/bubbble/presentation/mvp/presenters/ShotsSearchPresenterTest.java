package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotsSearchInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsSearchView;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsSearchView$$State;
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
    ShotsSearchInteractor shotsSearchInteractorMock;
    @Mock
    ShotsSearchView shotsSearchViewMock;
    @Mock
    ShotsSearchView$$State shotsSearchViewStateMock;

    private ShotsSearchPresenter shotsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shotsPresenter = new ShotsSearchPresenter(shotsSearchInteractorMock, new TestRxSchedulerProvider(), TEST_SEARCH_REQUEST);
        shotsPresenter.setViewState(shotsSearchViewStateMock);
    }

    @Test
    public void shots_shouldSearchAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        shotsPresenter.onFirstViewAttach();

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(shotsSearchInteractorMock).search(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        assertEquals(TEST_SEARCH_REQUEST, shotsCaptor.getValue().getSearchQuery());
        verify(shotsSearchViewStateMock).showNewShots(shots);
    }

    @Test
    public void onSearchQuery_shouldSearchAndShowShots() {
        //arrange
        String newSearchQuery = "Search request";

        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onNewSearchQuery(newSearchQuery);

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(shotsSearchInteractorMock, times(2)).search(shotsCaptor.capture());
        List<ShotsSearchRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(TEST_SEARCH_REQUEST, capturedRequestParams.get(0).getSearchQuery());
        assertEquals(newSearchQuery, capturedRequestParams.get(1).getSearchQuery());

        verify(shotsSearchViewStateMock).clearShotsList();
        verify(shotsSearchViewStateMock, times(2)).showNewShots(shots);
    }

    @Test
    public void onLoadMoreShotsRequest_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();
        shotsPresenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<ShotsSearchRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsSearchRequestParams.class);
        verify(shotsSearchInteractorMock, times(3)).search(shotsCaptor.capture());
        List<ShotsSearchRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(shotsSearchViewStateMock, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();

        // assert
        verify(shotsSearchInteractorMock).search(any(ShotsSearchRequestParams.class));
        verify(shotsSearchViewStateMock).hideShotsLoadingProgress();
        verify(shotsSearchViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.retryLoading();

        // assert
        verify(shotsSearchInteractorMock, times(2)).search(any(ShotsSearchRequestParams.class));
        verify(shotsSearchViewStateMock).hideNoNetworkLayout();
        verify(shotsSearchViewStateMock, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();
        shotsPresenter.retryLoading();

        // assert
        verify(shotsSearchInteractorMock, times(3)).search(any(ShotsSearchRequestParams.class));
        verify(shotsSearchViewStateMock, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();

        // assert
        verify(shotsSearchInteractorMock, times(1)).search(any(ShotsSearchRequestParams.class));
        verify(shotsSearchViewStateMock).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();

        // assert
        verify(shotsSearchInteractorMock, times(2)).search(any(ShotsSearchRequestParams.class));
        verify(shotsSearchViewStateMock).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(shotsSearchInteractorMock.search(any(ShotsSearchRequestParams.class)))
                .thenReturn(Single.just(shots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onShotClick(2);

        // assert
        verify(shotsSearchViewStateMock).openShotDetailsScreen(shots.get(2).getId());
    }

    private List<Shot> shots() {
        List<Shot> repositories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            repositories.add(new Shot());
        }
        return repositories;
    }

}
