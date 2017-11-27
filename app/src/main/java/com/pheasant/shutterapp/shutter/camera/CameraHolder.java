package com.pheasant.shutterapp.shutter.camera;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.pheasant.shutterapp.shutter.ui.interfaces.CameraHolderInterface;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraHolderListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

@SuppressWarnings("deprecation")
public class CameraHolder implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.AutoFocusCallback, Camera.FaceDetectionListener, CameraHolderInterface {

    private final double ASPECT_RATIO_TOLERANCE = 0.1;
    private final double ASPECT_RATIO_TARGET = 16d/9;

    private Camera camera;
    private Camera.Size cameraPreviewSize;
    private SurfaceHolder surfaceHolder;

    private CameraHolderListener cameraListener;

    private int cameraId;
    private int cameraFlashMode;
    private CameraFocus cameraFocusMode;

    public CameraHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCameraListener(CameraHolderListener cameraListener) {
        this.cameraListener = cameraListener;
    }

    // Camera Interface

    @Override
    public void changeCamera(int cameraId) {
        this.releaseCamera();
        if (cameraId > 0 && this.isFrontCameraSupported()) {
            this.cameraId = cameraId;
        } else {
            this.cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            if (this.cameraListener != null)
                this.cameraListener.onErrorMessage("Front camera not found!");
        }
        this.openCamera(this.cameraId);
    }

    @Override
    public void changeFlashMode(int flashMode) {
        if (this.inCameraMode(Camera.CameraInfo.CAMERA_FACING_BACK)) {
            this.setFlashMode(flashMode);
        } else {
            if (this.cameraListener != null)
                this.cameraListener.onErrorMessage("Flash is only available with back camera!");
        }
    }

    @Override
    public void changeFocusMode(CameraFocus focusMode) {
        this.cameraFocusMode = focusMode;
        switch (focusMode) {
            case FOCUS_MODE_AUTO: this.camera.stopFaceDetection(); this.setupAutoFocus(); break;
            case FOCUS_MODE_FACE: this.camera.startFaceDetection(); break;
            case FOCUS_MODE_POINT: this.camera.stopFaceDetection(); break;
        }
    }

    @Override
    public void setFocusPoint(int fixedX, int fixedY, int areaSize, int areaWeight) {
        final Camera.Area focusArea = CameraUtility.getFocusArea(fixedX, fixedY, areaSize, areaWeight);
        final List<Camera.Area> focusAreas = new ArrayList<>();
        focusAreas.add(focusArea);
        if (this.cameraFocusMode == CameraFocus.FOCUS_MODE_POINT)
            this.setFocusToAreas(focusAreas);
    }

    @Override
    public void takePhoto() {
        try { this.camera.autoFocus(this); }
        catch(Exception e) { e.printStackTrace(); }
    }

    // HW Camera Callbacks

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        this.camera.takePicture(null, null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        final Bitmap cameraPhoto = CameraUtility.getBitmapFromArray(data, this.cameraId);
        // wake up continuous focus
        this.camera.stopPreview();
        this.camera.startPreview();
        this.camera.cancelAutoFocus();
        // start preview
        if (this.cameraListener != null)
            this.cameraListener.onPhotoTaken(cameraPhoto);
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length > 0) {
            if (this.cameraListener != null)
                this.cameraListener.onNewFacesDetected(this.getFacesList(faces));
            if (this.cameraFocusMode == CameraFocus.FOCUS_MODE_FACE)
                this.setFocusToAreas(this.getAreaFocusList(faces));
        }
    }

    // Surface Holder

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.camera != null)
                this.camera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.camera != null)
            this.camera.stopPreview();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if(this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            if (this.cameraPreviewSize != null)
                parameters.setPreviewSize(this.cameraPreviewSize.width, this.cameraPreviewSize.height);
//            this.surfaceView.requestLayout();
            this.camera.setParameters(parameters);
            this.camera.startPreview();
        }
    }

    // Utils

    public void onStart() {
        this.openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public void onStop() {
        this.stopCamera();
    }

    private void releaseCamera() {
        if(this.camera != null) {
            this.camera.stopPreview();
            this.camera.release();
        }
    }

    private void stopCamera() {
        this.releaseCamera();
        if(this.camera != null)
            this.camera = null;
    }

    // Setup Camera

    private void openCamera(int id) {
        this.cameraId = id;
//        this.setUpCameraVector();
        try{
            this.camera = Camera.open(id);
            if (this.surfaceHolder != null)
                this.camera.setPreviewDisplay(this.surfaceHolder);
            this.setupCamera();
            if (this.cameraListener != null)
                this.cameraListener.onCameraChanged(this.cameraId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupCamera() {
        if (this.camera != null) {
            for (Camera.Size previewSize : this.camera.getParameters().getSupportedPreviewSizes()) {
                double currentAspectRatio = (double) previewSize.width / previewSize.height;
                if (Math.abs(currentAspectRatio - ASPECT_RATIO_TARGET) < ASPECT_RATIO_TOLERANCE) {
                    this.cameraPreviewSize = previewSize; break;
                }
            }
            // setup params
            Camera.Parameters params = this.camera.getParameters();
            if (this.cameraPreviewSize != null)
                params.setPictureSize(this.cameraPreviewSize.width, this.cameraPreviewSize.height);
            params.setAutoWhiteBalanceLock(false);
            params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            params.setAutoExposureLock(false);
            this.camera.setParameters(params);
            this.camera.setFaceDetectionListener(this);
            this.camera.setDisplayOrientation(90); // vertical camera
            this.camera.startPreview();
        }
    }

    // Setup Flash

    private void setFlashMode(int flashMode) {
        this.cameraFlashMode = flashMode;
        Camera.Parameters params = this.camera.getParameters();
        switch (this.cameraFlashMode) {
            case 0: params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF); break;
            case 1: params.setFlashMode(Camera.Parameters.FLASH_MODE_ON); break;
            case 2: params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO); break;
        }
        this.camera.setParameters(params);
        if (this.cameraListener != null)
            this.cameraListener.onFlashModeChanged(flashMode);
    }

    // Focus utility

    private void setupAutoFocus() {
        Camera.Parameters params = this.camera.getParameters();
        if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusAreas(null);
            params.setMeteringAreas(null);
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (params.getSupportedFocusModes().contains(Camera.Parameters.FLASH_MODE_AUTO)) {
            params.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
        }
        this.camera.setParameters(params);
    }

    private ArrayList<Camera.Face> getFacesList(Camera.Face[] faces) {
        final ArrayList<Camera.Face> facesList = new ArrayList<>();
        for (Camera.Face face : faces)
            facesList.add(face);
        return facesList;
    }

    private ArrayList<Camera.Area> getAreaFocusList(Camera.Face[] faces) {
        ArrayList<Camera.Area> faceFocusList = new ArrayList<>();
        for (Camera.Face face : faces)
            faceFocusList.add(new Camera.Area(face.rect, face.score));
        return faceFocusList;
    }

    private void setFocusToAreas(List<Camera.Area> focusList) {
        try {
            Camera.Parameters param = this.camera.getParameters();
            param.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            param.setFocusAreas(focusList);
            param.setMeteringAreas(focusList);
            this.camera.setParameters(param);
            this.camera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Others

    private boolean isFrontCameraSupported() {
        return (Camera.getNumberOfCameras() > 1 ? true : false);
    }

    private boolean inCameraMode(int cameraId) {
        return (this.cameraId == cameraId ? true : false);
    }
}