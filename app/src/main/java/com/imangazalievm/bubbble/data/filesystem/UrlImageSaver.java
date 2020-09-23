package com.imangazalievm.bubbble.data.filesystem;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import javax.inject.Inject;

public class UrlImageSaver {

    private Context context;
    private File destinationFolder;

    @Inject
    public UrlImageSaver(Context context) {
        this.context = context;
        this.destinationFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public void saveImage(String imageUrl) {
        String imageFileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1, imageUrl.length());
        Uri uri = Uri.parse(imageUrl);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(imageFileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(destinationFolder));

        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadmanager.enqueue(request);
    }

}
