package com.pheasant.shutterapp.shutter.ui.listeners;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-27.
 */

public interface CameraEditorListener {
    void onPhotoEdited(Bitmap finalBitmap);
    void onPhotoRejected();
}
