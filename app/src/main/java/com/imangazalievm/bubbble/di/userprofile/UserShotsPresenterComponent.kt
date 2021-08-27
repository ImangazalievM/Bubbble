package com.imangazalievm.bubbble.di.userprofile;

import com.imangazalievm.bubbble.di.global.ApplicationComponent;
import com.imangazalievm.bubbble.di.global.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserShotsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserShotsPresenterModule.class)
public interface UserShotsPresenterComponent {

    UserShotsPresenter getPresenter();

}
