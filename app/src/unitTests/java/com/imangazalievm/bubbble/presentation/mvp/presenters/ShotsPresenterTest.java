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
    ShotsInteractor interactorMock;
    @Mock
    ShotsView shotsViewMock;
    @Mock
    ShotsView$$State shotsViewStateMock;

    private ShotsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShotsPresenter(interactorMock, new TestRxSchedulerProvider(), TEST_SORT_TYPE);
        presenter.setViewState(shotsViewStateMock);
    }

    @Test
    public void shots_shouldLoadAndShowShotsOnFirstAttach() {
        //arrange
        List<Shot> shots = new ArrayList<>();
        when(interactorMock.getShots(any(ShotsRequestParams.class))).thenReturn(Single.just(shots));

        //act
        presenter.onFirstViewAttach();

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(interactorMock).getShots(shotsCaptor.capture());
        assertEquals(1, shotsCaptor.getValue().getPage());
        verify(shotsViewStateMock).showNewShots(shots);
    }

    @Test
    public void shots_shouldCorrectLoadNextShots() {
        //arrange
        List<Shot> newShots = shots();
        when(interactorMock.getShots(any(ShotsRequestParams.class))).thenReturn(Single.just(newShots));

        // act
        presenter.onFirstViewAttach();
        presenter.onLoadMoreShotsRequest();
        presenter.onLoadMoreShotsRequest();

        // assert
        ArgumentCaptor<ShotsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotsRequestParams.class);
        verify(interactorMock, times(3)).getShots(shotsCaptor.capture());
        List<ShotsRequestParams> capturedsRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedsRequestParams.get(0).getPage());
        assertEquals(2, capturedsRequestParams.get(1).getPage());
        assertEquals(3, capturedsRequestParams.get(2).getPage());
        verify(shotsViewStateMock, times(3)).showNewShots(newShots);
    }

    @Test
    public void shots_shouldShowNoNetworkLayout() {
        //arrange
        when(interactorMock.getShots(any(ShotsRequestParams.class))).thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.onFirstViewAttach();

        // assert
        verify(interactorMock).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadFirstPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(shots));

        // act
        presenter.onFirstViewAttach();
        presenter.retryLoading();

        // assert
        verify(interactorMock, times(2)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideNoNetworkLayout();
        verify(shotsViewStateMock, times(2)).showShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldRetryLoadNextPageAndShowProgress() {
        //arrange
        List<Shot> shots = shots();
        when(interactorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.onFirstViewAttach();
        presenter.onLoadMoreShotsRequest();
        presenter.retryLoading();

        // assert
        verify(interactorMock, times(3)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock, times(2)).showShotsLoadingMoreProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.onFirstViewAttach();

        // assert
        verify(interactorMock, times(1)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideShotsLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideLoadingMoreProgressOnError() {
        //arrange
        List<Shot> shots = shots();
        when(interactorMock.getShots(any(ShotsRequestParams.class)))
                .thenReturn(Single.just(shots))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.onFirstViewAttach();
        presenter.onLoadMoreShotsRequest();

        // assert
        verify(interactorMock, times(2)).getShots(any(ShotsRequestParams.class));
        verify(shotsViewStateMock).hideShotsLoadingMoreProgress();
    }

    private List<Shot> shots() {
        List<Shot> repositories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            repositories.add(new Shot());
        }
        return repositories;
    }

}
