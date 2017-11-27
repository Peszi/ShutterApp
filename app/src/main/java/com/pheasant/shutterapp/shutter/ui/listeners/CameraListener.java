package com.pheasant.shutterapp.shutter.ui.listeners;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-27.
 */

public interface CameraListener {
    void onPhotoEvent(Bitmap cameraPhoto);
    void onCameraErrorMessageEvent(String message);
}
