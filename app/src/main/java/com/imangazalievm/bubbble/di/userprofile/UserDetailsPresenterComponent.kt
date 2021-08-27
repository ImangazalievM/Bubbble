package com.imangazalievm.bubbble.di.userprofile;

import com.imangazalievm.bubbble.di.global.ApplicationComponent;
import com.imangazalievm.bubbble.di.global.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserDetailsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserDetailsPresenterModule.class)
public interface UserDetailsPresenterComponent {

    UserDetailsPresenter getPresenter();

}
