package com.imangazalievm.bubbble.di;

import com.imangazalievm.bubbble.di.modules.ApplicationModule;
import com.imangazalievm.bubbble.di.modules.DataModule;
import com.imangazalievm.bubbble.di.qualifiers.IoScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import ru.caprice.cafe.di.modules.OkHttpInterceptorsModule;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, OkHttpInterceptorsModule.class})
public interface ApplicationComponent {

    @IoScheduler
    Scheduler provideJobScheduler();

    @UiScheduler
    Scheduler provideUiScheduler();

    IShotsRepository provideBookRepository();

    ICommentsRepository provideCommentsRepository();

    IUsersRepository provideUsersRepository();

    IFollowersRepository provideFollowersRepository();

    IImagesRepository provideImagesRepository();

}
