package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.data.global.filesystem.UrlImageSaver
import io.reactivex.Completable
import io.reactivex.CompletableOnSubscribe
import io.reactivex.CompletableEmitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val urlImageSaver: UrlImageSaver
) {

    suspend fun saveImage(shotImageUrl: String) = withContext(Dispatchers.IO) {
        urlImageSaver.saveImage(shotImageUrl)
    }

}