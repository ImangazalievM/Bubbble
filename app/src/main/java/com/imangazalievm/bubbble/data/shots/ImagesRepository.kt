package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.data.global.filesystem.UrlImageSaver
import io.reactivex.Completable
import io.reactivex.CompletableOnSubscribe
import io.reactivex.CompletableEmitter
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val urlImageSaver: UrlImageSaver
) {

    fun saveImage(shotImageUrl: String?): Completable {
        return Completable.create { e: CompletableEmitter ->
            urlImageSaver.saveImage(shotImageUrl)
            e.onComplete()
        }
    }

}