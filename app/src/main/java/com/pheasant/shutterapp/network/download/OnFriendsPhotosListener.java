package com.pheasant.shutterapp.network.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.network.request.data.PhotoData;
import com.pheasant.shutterapp.network.request.util.PhotoRequest;
import com.pheasant.shutterapp.network.request.util.Request;

import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public interface OnFriendsPhotosListener {
    void onPhotosListPrepared(List<PhotoData> imagesList);
    void onPhotoPrepared(Bitmap photoBitmap, int photoId);
}
