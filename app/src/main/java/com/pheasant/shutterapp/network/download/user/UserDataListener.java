package com.pheasant.shutterapp.network.download.user;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Peszi on 2017-05-24.
 */

public interface UserDataListener {
    void onListLoaded(List<UserPhoto> userPhotos);
    void onPhotoLoaded(int photoId, Bitmap bitmap);
}
