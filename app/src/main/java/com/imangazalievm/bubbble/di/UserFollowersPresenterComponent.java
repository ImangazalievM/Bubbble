package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.UserFollowersPresenterModule;
import com.imangazalievm.bubbble.di.modules.UserProfilePresenterModule;
import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserFollowersPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = UserFollowersPresenterModule.class)
public interface UserFollowersPresenterComponent {

    UserFollowersPresenter getPresenter();

}
