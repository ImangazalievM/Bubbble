package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotsInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsView;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsView$$State;
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
public class ShotsPresenterTest {

    private static final String TEST_SORT_TYPE = "sort_type";

    @Mock
    ShotsInteractor shotsInteractorMock;
    @Mock
    ShotsView shotsViewMock;
    @Mock
    ShotsView$$State shotsViewStateMock;

    private ShotsPresenter shotsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shotsPresenter = new ShotsPresenter(shotsInteractorMock, new TestRxSchedulerProvider(), TEST_SORT_TYPE);
        shotsPresenter.setViewState(shotsViewStateMock);
    }

    @Test
    public void shots_shouldLoadAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        //act
        shotsPresenter.onFirstViewAttach();

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(shotsInteractorMock).getShots(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        verify(shotsViewStateMock).showNewShots(shots);
    }

    @Test
    public void onLoadMoreShotsRequest_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(newShots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();
        shotsPresenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(shotsInteractorMock, times(3)).getShots(shotsCaptor.capture());
        List<ShotsRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(shotsViewStateMock, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();

        // assert
        verify(shotsInteractorMock).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideShotsLoadingProgress();
        verify(shotsViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.retryLoading();

        // assert
        verify(shotsInteractorMock, times(2)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideNoNetworkLayout();
        verify(shotsViewStateMock, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();
        shotsPresenter.retryLoading();

        // assert
        verify(shotsInteractorMock, times(3)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();

        // assert
        verify(shotsInteractorMock, times(1)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onLoadMoreShotsRequest();

        // assert
        verify(shotsInteractorMock, times(2)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideShotsLoadingMoreProgress();
    }

    @Test
    public void onShotClick_shouldOpenShotDetailsScreen() {
        //arrange
        List<Shot> shots = shots();
        when(shotsInteractorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots));

        // act
        shotsPresenter.onFirstViewAttach();
        shotsPresenter.onShotClick(2);

        // assert
        verify(shotsViewStateMock).openShotDetailsScreen(shots.get(2).getId());
    }

    private List<Shot> shots() {
        List<Shot> repositories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            repositories.add(new Shot());
        }
        return repositories;
    }

}
