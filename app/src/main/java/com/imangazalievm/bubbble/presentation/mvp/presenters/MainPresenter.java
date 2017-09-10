package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotsSearchInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.MainView;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsSearchView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public MainPresenter() {
    }

    public void onSearchQuery(String searchQuery) {
        getViewState().openSearchScreen(searchQuery);
    }

}
