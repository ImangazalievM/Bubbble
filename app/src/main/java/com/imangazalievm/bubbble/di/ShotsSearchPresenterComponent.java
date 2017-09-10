package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.ShotsSearchPresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotsSearchPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotsSearchPresenterModule.class)
public interface ShotsSearchPresenterComponent {

    ShotsSearchPresenter getPresenter();

}
