package com.pheasant.shutterapp.shutter.listeners;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraEventListener {
    void onCameraChanged(int cameraId);
    void onFlashModeChanged(int flashMode);
    void onPhotoTaken(Bitmap cameraPhoto);
    void onErrorMessage(String message);
}
