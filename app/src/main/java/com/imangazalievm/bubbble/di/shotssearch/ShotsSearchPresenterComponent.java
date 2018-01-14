package com.imangazalievm.bubbble.di.shotssearch;

import com.imangazalievm.bubbble.di.global.ApplicationComponent;
import com.imangazalievm.bubbble.di.global.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.shotssearch.ShotsSearchPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotsSearchPresenterModule.class)
public interface ShotsSearchPresenterComponent {

    ShotsSearchPresenter getPresenter();

}
