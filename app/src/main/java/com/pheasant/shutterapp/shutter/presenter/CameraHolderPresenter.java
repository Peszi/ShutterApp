package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;
import android.hardware.Camera;

import com.pheasant.shutterapp.shutter.camera.CameraFocus;
import com.pheasant.shutterapp.shutter.camera.CameraSurface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraHolderInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraPreviewView;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraSurfaceInterface;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraHolderListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraPreviewListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraSurfaceListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraHolderPresenter implements CameraPreviewListener, CameraHolderListener, CameraSurfaceListener {

    private final int FOCUS_AREA_SIZE = 300;
    private final int FOCUS_AREA_WIEGHT = 1000;

    private CameraHolderInterface cameraHolderInterface;
    private CameraSurfaceInterface surfaceInterface;

    private CameraPreviewView cameraView;
    private CameraListener cameraListener;

    private int cameraId;
    private int cameraFlashMode;

    public void setCameraHolderInterface(CameraHolderInterface cameraHolderInterface) {
        this.cameraHolderInterface = cameraHolderInterface;
    }

    public void setCameraSurfaceInterface(CameraSurface surfaceInterface) {
        this.surfaceInterface = surfaceInterface;
    }

    public void setCameraViewInterface(CameraPreviewView cameraView) {
        this.cameraView = cameraView;
    }

    public void setCameraListener(CameraListener cameraListener) {
        this.cameraListener = cameraListener;
    }

    // UI callbacks

    @Override
    public void onTakePhotoEvent() {
        this.cameraHolderInterface.takePhoto();
        this.cameraView.startTakePhotoAnimation();
    }

    @Override
    public void onSwapCameraEvent() {
        this.cameraId++;
        if (this.cameraId >= 2) { this.cameraId = 0; }
        this.surfaceInterface.setCamera(this.cameraId);
        this.cameraHolderInterface.changeCamera(this.cameraId);
    }

    @Override
    public void onChangeFlashModeEvent() {
        this.cameraFlashMode++;
        if (this.cameraFlashMode >= 3) { this.cameraFlashMode = 0; }
        this.cameraHolderInterface.changeFlashMode(this.cameraFlashMode);
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
    public void onNewFacesDetected(ArrayList<Camera.Face> newFaces) {
        this.surfaceInterface.drawFacesPointers(newFaces);
    }

    @Override
    public void onPhotoTaken(Bitmap cameraPhoto) {
        if (this.cameraListener != null)
            this.cameraListener.onPhotoEvent(cameraPhoto);
    }

    @Override
    public void onErrorMessage(String message) {
        if (this.cameraListener != null)
            this.cameraListener.onCameraErrorMessageEvent(message);
    }

    private void setFaceFocus() {
        this.cameraView.showAutoFocusIcon(true);
        this.cameraView.showFaceFocusIcon(false);
        this.cameraHolderInterface.changeFocusMode(CameraFocus.FOCUS_MODE_FACE);
        this.surfaceInterface.initFacePointers();
    }

    private void setAutoFocus() {
        this.cameraView.showAutoFocusIcon(false);
        this.cameraView.showFaceFocusIcon(true);
        this.cameraHolderInterface.changeFocusMode(CameraFocus.FOCUS_MODE_AUTO);
    }

    private void setPointFocus(int fixedX, int fixedY) {
        this.cameraView.showAutoFocusIcon(true);
        this.cameraView.showFaceFocusIcon(true);
        this.cameraHolderInterface.changeFocusMode(CameraFocus.FOCUS_MODE_POINT);
        this.cameraHolderInterface.setFocusPoint(fixedX, fixedY, this.FOCUS_AREA_SIZE, this.FOCUS_AREA_WIEGHT);
    }
}
