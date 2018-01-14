package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule;
import com.imangazalievm.bubbble.di.modules.TestApplicationModule;
import com.imangazalievm.bubbble.domain.repository.CommentsRepository;
import com.imangazalievm.bubbble.domain.repository.FollowersRepository;
import com.imangazalievm.bubbble.domain.repository.ImagesRepository;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;
import com.imangazalievm.bubbble.domain.repository.UsersRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class, TestApplicationModule.class, OkHttpInterceptorsModule.class})
public interface TestApplicationComponent extends ApplicationComponent{

    ShotsRepository provideBookRepository();

    CommentsRepository provideCommentsRepository();

    UsersRepository provideUsersRepository();

    FollowersRepository provideFollowersRepository();

    ImagesRepository provideImagesRepository();

}
