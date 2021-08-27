package com.imangazalievm.bubbble.di.global;

import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule;
import com.imangazalievm.bubbble.domain.global.repositories.CommentsRepository;
import com.imangazalievm.bubbble.domain.global.repositories.FollowersRepository;
import com.imangazalievm.bubbble.domain.global.repositories.ImagesRepository;
import com.imangazalievm.bubbble.domain.global.repositories.ShotsRepository;
import com.imangazalievm.bubbble.domain.global.repositories.UsersRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, OkHttpInterceptorsModule.class})
public interface ApplicationComponent {

    ShotsRepository provideBookRepository();

    CommentsRepository provideCommentsRepository();

    UsersRepository provideUsersRepository();

    FollowersRepository provideFollowersRepository();

    ImagesRepository provideImagesRepository();

}
