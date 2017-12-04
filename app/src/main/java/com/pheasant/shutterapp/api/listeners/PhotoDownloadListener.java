package com.pheasant.shutterapp.api.listeners;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-30.
 */

public interface PhotoDownloadListener {
    void onPhoto(int photoId, Bitmap photoBitmap);
    void onThumbnail(int photoId, Bitmap photoBitmap);
}
