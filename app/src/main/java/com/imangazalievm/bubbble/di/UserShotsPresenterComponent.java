package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.UserShotsPresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserProfilePresenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserShotsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserShotsPresenterModule.class)
public interface UserShotsPresenterComponent {

    UserShotsPresenter getPresenter();

}
