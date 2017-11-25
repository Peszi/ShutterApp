package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;

import com.pheasant.shutterapp.shutter.camera.CameraFocus;
import com.pheasant.shutterapp.shutter.camera.CameraSurface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraPreviewView;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraSurfaceInterface;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraHolderListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraPreviewListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraSurfaceListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraHolderPresenter implements CameraPreviewListener, CameraHolderListener, CameraSurfaceListener {

    private final int FOCUS_AREA_SIZE = 300;
    private final int FOCUS_AREA_WIEGHT = 1000;

    private CameraInterface cameraInterface;
    private CameraSurfaceInterface surfaceInterface;
    private CameraPreviewView cameraView;

    private int cameraId;
    private int cameraFlashMode;

    public void setCameraInterface(CameraInterface cameraInterface) {
        this.cameraInterface = cameraInterface;
    }

    public void setCameraSurfaceInterface(CameraSurface surfaceInterface) {
        this.surfaceInterface = surfaceInterface;
    }

    public void setCameraViewInterface(CameraPreviewView cameraView) {
        this.cameraView = cameraView;
    }

    // UI callbacks

    @Override
    public void onTakePhotoEvent() {
        this.cameraInterface.takePhoto();
        this.cameraView.startTakePhotoAnimation();
    }

    @Override
    public void onSwapCameraEvent() {
        this.cameraId++;
        if (this.cameraId >= 2) { this.cameraId = 0; }
        this.cameraInterface.changeCamera(this.cameraId);
    }

    @Override
    public void onChangeFlashModeEvent() {
        this.cameraFlashMode++;
        if (this.cameraFlashMode >= 3) { this.cameraFlashMode = 0; }
        this.cameraInterface.changeFlashMode(this.cameraFlashMode);
    }

    @Override
    public void onFaceFocusEvent() {
        this.setFaceFocus();
    }

    @Override
    public void onAutoFocusEvent() {
        this.setAutoFocus();
    }

    // Camera Surface Callbacks

    @Override
    public void onTouchDownEvent(int x, int y, int fixedX, int fixedY) {
        this.setPointFocus(fixedX, fixedY);
        this.surfaceInterface.drawFocusPointer(x, y);
    }

    // Camera Callbacks

    @Override
    public void onCameraChanged(int cameraId) {
        this.setAutoFocus();
        this.cameraView.changeCameraSwapIcon(cameraId);
    }

    @Override
    public void onFlashModeChanged(int flashMode) {
        this.cameraView.changeFlashModeIcon(flashMode);
    }

    @Override
    public void onPhotoTaken(Bitmap cameraPhoto) {

    }

    @Override
    public void onNewFacesDetected(ArrayList<Camera.Face> newFaces) {
        Log.d("RESPONSE", "new faces " + newFaces.size());
    }

    @Override
    public void onErrorMessage(String message) {

    }

    private void setFaceFocus() {
        this.cameraView.showAutoFocusIcon(true);
        this.cameraView.showFaceFocusIcon(false);
        this.cameraInterface.changeFocusMode(CameraFocus.FOCUS_MODE_FACE);
    }

    private void setAutoFocus() {
        this.cameraView.showAutoFocusIcon(false);
        this.cameraView.showFaceFocusIcon(true);
        this.cameraInterface.changeFocusMode(CameraFocus.FOCUS_MODE_AUTO);
    }

    private void setPointFocus(int fixedX, int fixedY) {
        this.cameraView.showAutoFocusIcon(true);
        this.cameraView.showFaceFocusIcon(true);
        this.cameraInterface.changeFocusMode(CameraFocus.FOCUS_MODE_POINT);
        this.cameraInterface.setFocusPoint(fixedX, fixedY, this.FOCUS_AREA_SIZE, this.FOCUS_AREA_WIEGHT);
    }
}
