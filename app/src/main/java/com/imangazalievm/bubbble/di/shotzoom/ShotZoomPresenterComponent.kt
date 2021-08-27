package com.imangazalievm.bubbble.di.shotzoom;

import com.imangazalievm.bubbble.di.global.ApplicationComponent;
import com.imangazalievm.bubbble.di.global.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.shotzoom.ShotZoomPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = ShotZoomPresenterModule.class)
public interface ShotZoomPresenterComponent {

    ShotZoomPresenter getPresenter();

}
