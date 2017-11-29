package com.pheasant.shutterapp.shutter.ui.listeners;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-29.
 */

public interface CameraEditorViewListener {
    void onPageShow();
    void onNewPhoto(Bitmap photoBitmap);
    void onEditModeChange(int index);
    void onPreviewAcceptEvent();
    void onPreviewRejectEvent();
    Bitmap getEditedPhoto();
}
