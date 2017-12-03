package com.pheasant.shutterapp.shutter.ui.interfaces;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-30.
 */

public interface BrowsePhotosView {
    void listScrollToPosition(int position);
    void showPhotoDialog(Bitmap photoBitmap);
    void updatePhotosList(ArrayList<PhotoData> photosList);
    void refreshSetRefreshing(boolean show);
    void updateThumbnail(int photoId, Bitmap bitmap);
}
