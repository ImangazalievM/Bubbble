package com.bubbble.data.images

import com.bubbble.data.global.filesystem.UrlImageSaver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepository @Inject constructor(
    private val urlImageSaver: UrlImageSaver
) {

    suspend fun saveImage(shotImageUrl: String) = withContext(Dispatchers.IO) {
        urlImageSaver.saveImage(shotImageUrl)
    }

}