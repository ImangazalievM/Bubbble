package com.bubbble.data.global.filesystem

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import javax.inject.Inject

class UrlImageSaver @Inject constructor(private val context: Context) {

    private val destinationFolder: File =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    suspend fun saveImage(imageUrl: String) {
        val imageFileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1, imageUrl.length)
        val uri = Uri.parse(imageUrl)
        val request = DownloadManager.Request(uri)
        request.setTitle(imageFileName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationUri(Uri.fromFile(destinationFolder))
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

}