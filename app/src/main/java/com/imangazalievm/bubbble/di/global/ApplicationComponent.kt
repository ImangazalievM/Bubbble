package com.imangazalievm.bubbble.di.global

import com.imangazalievm.bubbble.data.shots.CommentsRepository
import com.imangazalievm.bubbble.data.shots.ImagesRepository
import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.data.users.FollowersRepository
import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule
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
    fun provideBookRepository(): ShotsRepository
    fun provideCommentsRepository(): CommentsRepository
    fun provideUsersRepository(): UsersRepository
    fun provideFollowersRepository(): FollowersRepository
    fun provideImagesRepository(): ImagesRepository
}