package com.bubbble.di

import com.bubbble.data.shots.CommentsRepository
import com.bubbble.data.shots.ImagesRepository
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.data.users.FollowersRepository
import com.bubbble.data.users.UsersRepository
import com.bubbble.di.modules.OkHttpInterceptorsModule
import com.bubbble.presentation.global.mvp.ErrorHandler
import com.bubbble.presentation.global.permissions.PermissionsManager
import com.bubbble.presentation.global.resourcesmanager.ResourcesManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        OkHttpInterceptorsModule::class
    ]
)
interface ApplicationComponent {

    val provideBookRepository: ShotsRepository
    val provideCommentsRepository: CommentsRepository
    val provideUsersRepository: UsersRepository
    val provideFollowersRepository: FollowersRepository
    val provideImagesRepository: ImagesRepository

    val errorHandler: ErrorHandler
    val resourcesManager: ResourcesManager
    val permissionsManager: PermissionsManager

}