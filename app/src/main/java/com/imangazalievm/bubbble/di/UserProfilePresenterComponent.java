package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.UserProfilePresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserFollowersPresenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserProfilePresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserProfilePresenterModule.class)
public interface UserProfilePresenterComponent {

    UserProfilePresenter getPresenter();

}
