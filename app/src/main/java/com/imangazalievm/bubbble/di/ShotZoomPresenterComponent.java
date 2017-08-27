package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.ShotZoomPresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotDetailsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotZoomPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotZoomPresenterModule.class)
public interface ShotZoomPresenterComponent {

    ShotZoomPresenter getPresenter();

}
