package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.ShotsPresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotsPresenterModule.class)
public interface ShotsPresenterComponent {

    ShotsPresenter getPresenter();

}
