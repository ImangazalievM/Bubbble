package com.imangazalievm.bubbble.di.global

import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule
import com.imangazalievm.bubbble.domain.global.repositories.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class,
        OkHttpInterceptorsModule::class
    ]
)
interface ApplicationComponent {
    fun provideBookRepository(): ShotsRepository?
    fun provideCommentsRepository(): CommentsRepository?
    fun provideUsersRepository(): UsersRepository?
    fun provideFollowersRepository(): FollowersRepository?
    fun provideImagesRepository(): ImagesRepository?
}