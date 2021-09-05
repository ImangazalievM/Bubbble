package com.bubbble.presentation.mvp.presenters;

import com.bubbble.shots.main.MainPresenter;
import com.bubbble.shots.main.MainView;
import com.bubbble.test.BubbbleTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

@RunWith(BubbbleTestRunner.class)
public class MainPresenterTest {

    @Mock
    private MainView view;
    private MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter();
    }

    @Test
    public void onSearchQuery_shouldOpenSearchScreen() {
        //arrange
        String testSearchQuery = "Test search query";

        //act
        presenter.attachView(view);
        presenter.onSearchQuery(testSearchQuery);

        // assert
        verify(view).openSearchScreen(testSearchQuery);
    }

}
