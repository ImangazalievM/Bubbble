package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserShotsPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class)
public interface UserShotsPresenterComponent {

    void inject(UserShotsPresenter presenter);

}
