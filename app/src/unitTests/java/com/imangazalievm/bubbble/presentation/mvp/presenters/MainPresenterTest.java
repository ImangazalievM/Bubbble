package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.interactors.ShotsInteractor;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.MainView;
import com.imangazalievm.bubbble.presentation.mvp.views.MainView$$State;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class MainPresenterTest {

    @Mock
    ShotsInteractor shotsInteractorMock;
    @Mock
    MainView$$State mainViewStateMock;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter();
        mainPresenter.setViewState(mainViewStateMock);
    }

    @Test
    public void onSearchQuery_shouldOpenSearchScreen() {
        //arrange
        String testSearchQuery = "Test search query";

        //act
        mainPresenter.onSearchQuery(testSearchQuery);

        // assert
        verify(mainViewStateMock).openSearchScreen(testSearchQuery);
    }

}
