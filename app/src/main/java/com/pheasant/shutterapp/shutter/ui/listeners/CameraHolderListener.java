package com.pheasant.shutterapp.shutter.ui.listeners;

import android.graphics.Bitmap;
import android.hardware.Camera;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraHolderListener {
    void onCameraChanged(int cameraId);
    void onFlashModeChanged(int flashMode);
    void onNewFacesDetected(ArrayList<Camera.Face> newFaces);
    void onPhotoTaken(Bitmap cameraPhoto);
    void onErrorMessage(String message);
}
