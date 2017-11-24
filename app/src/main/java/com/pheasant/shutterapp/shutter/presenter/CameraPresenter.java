package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.interfaces.CameraInterface;
import com.pheasant.shutterapp.shutter.interfaces.CameraPreviewView;
import com.pheasant.shutterapp.shutter.listeners.CameraEventListener;
import com.pheasant.shutterapp.shutter.listeners.CameraPreviewListener;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraPreviewFragment;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraPresenter implements CameraPreviewListener, CameraEventListener {

    private CameraInterface cameraInterface;

    private CameraPreviewView cameraView;

    public void setCameraInterface(CameraInterface cameraInterface) {
        this.cameraInterface = cameraInterface;
    }

    public void setCameraView(CameraPreviewFragment cameraView) {
        this.cameraView = cameraView;
    }

    // UI callbacks

    @Override
    public void onTakePhotoEvent() {

    }

    @Override
    public void onSwapCameraEvent() {

    }

    @Override
    public void onChangeFlashModeEvent() {

    }

    @Override
    public void onFaceFocusEvent() {

    }

    @Override
    public void onAutoFocusEvent() {

    }

    // Camera Callbacks

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
