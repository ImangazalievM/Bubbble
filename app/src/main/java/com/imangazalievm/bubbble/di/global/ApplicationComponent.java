package com.imangazalievm.bubbble.di.global;

import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule;
import com.imangazalievm.bubbble.domain.global.repository.CommentsRepository;
import com.imangazalievm.bubbble.domain.global.repository.FollowersRepository;
import com.imangazalievm.bubbble.domain.global.repository.ImagesRepository;
import com.imangazalievm.bubbble.domain.global.repository.ShotsRepository;
import com.imangazalievm.bubbble.domain.global.repository.UsersRepository;

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
