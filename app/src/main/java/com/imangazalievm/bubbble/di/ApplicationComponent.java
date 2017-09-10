package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.ApplicationModule;
import com.imangazalievm.bubbble.di.modules.DataModule;
import com.imangazalievm.bubbble.domain.repository.CommentsRepository;
import com.imangazalievm.bubbble.domain.repository.FollowersRepository;
import com.imangazalievm.bubbble.domain.repository.ImagesRepository;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;
import com.imangazalievm.bubbble.domain.repository.UsersRepository;

import javax.inject.Singleton;

import dagger.Component;
import ru.caprice.cafe.di.modules.OkHttpInterceptorsModule;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, OkHttpInterceptorsModule.class})
public interface ApplicationComponent {

    ShotsRepository provideBookRepository();

    CommentsRepository provideCommentsRepository();

    UsersRepository provideUsersRepository();

    FollowersRepository provideFollowersRepository();

    ImagesRepository provideImagesRepository();

}
