package com.pheasant.shutterapp.network.download;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;

import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public interface OnFriendsPhotosListener {
    void onPhotosListPrepared(List<PhotoData> imagesList);
    void onPhotoPrepared(Bitmap photoBitmap, int photoId);
}
