package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.UserDetailsPresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserDetailsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserDetailsPresenterModule.class)
public interface UserDetailsPresenterComponent {

    UserDetailsPresenter getPresenter();

}
