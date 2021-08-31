package com.imangazalievm.bubbble.di

import com.imangazalievm.bubbble.data.shots.CommentsRepository
import com.imangazalievm.bubbble.data.shots.ImagesRepository
import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.data.users.FollowersRepository
import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.di.modules.OkHttpInterceptorsModule
import com.imangazalievm.bubbble.presentation.global.mvp.ErrorHandler
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import com.imangazalievm.bubbble.presentation.global.resourcesmanager.ResourcesManager
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