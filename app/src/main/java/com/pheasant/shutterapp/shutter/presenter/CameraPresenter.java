package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.interfaces.CameraInterface;
import com.pheasant.shutterapp.shutter.listeners.CameraEventListener;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraPresenter implements CameraEventListener {

    private CameraInterface cameraInterface;

    public void setCameraInterface(CameraInterface cameraInterface) {
        this.cameraInterface = cameraInterface;
    }

    @Override
    public void onCameraChanged(int cameraId) {

    }

    @Override
    public void onFlashModeChanged(int flashMode) {

    }

    @Override
    public void onPhotoTaken(Bitmap cameraPhoto) {

    }

    @Override
    public void onErrorMessage(String message) {

    }
}
