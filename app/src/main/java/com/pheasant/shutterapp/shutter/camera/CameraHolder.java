package com.pheasant.shutterapp.shutter.camera;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.pheasant.shutterapp.shutter.interfaces.CameraInterface;
import com.pheasant.shutterapp.shutter.listeners.CameraEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

@SuppressWarnings("deprecation")
public class CameraHolder implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.AutoFocusCallback, SurfaceView.OnTouchListener, Camera.FaceDetectionListener, CameraInterface {

    private final int FOCUS_AREA_SIZE = 300;

    private final double ASPECT_RATIO_TOLERANCE = 0.1;
    private final double ASPECT_RATIO_TARGET = 16d/9;

    private Camera camera;
    private Camera.Size cameraPreviewSize;
    private Camera.Face[] cameraDetectedFaces;

    private CameraSurface surfaceView;
    private SurfaceHolder surfaceHolder;

    private CameraEventListener cameraListener;

    private int cameraId;
    private int cameraFlashMode;

    public CameraHolder(CameraSurface cameraSurface) {
        this.surfaceView = cameraSurface;
        this.surfaceView.setOnTouchListener(this);
        this.surfaceHolder = surfaceView.getHolder();
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCameraListener(CameraEventListener cameraListener) {
        this.cameraListener = cameraListener;
    }

    @Override
    public void changeCamera(int cameraId) {
        this.releaseCamera();
        if (cameraId > 0 && this.isFrontCameraSupported()) {
            this.cameraId = cameraId;
        } else {
            this.cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            if (this.cameraListener != null)
                this.cameraListener.onErrorMessage("Front camera not founds!");
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
    public void takePhoto() {
        try {
            this.camera.autoFocus(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnFaceFocus() {

    }

    @Override
    public void setAutoFocus() {

    }

    @Override
    public void setPointFocus() {

    }

    /* ===================================== TAKE PHOTO ========================================= */

    private void openCamera(int id) {
        this.cameraId = id;
        this.setUpVector();
        try{
            this.camera = Camera.open(id);
            if (this.surfaceHolder != null)
                this.camera.setPreviewDisplay(this.surfaceHolder);
            this.setupCamera();
            this.switchToAutoFocus();
            if (this.cameraListener != null)
                this.cameraListener.onCameraChanged(this.cameraId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFlashMode(int flashMode) {
        this.cameraFlashMode = flashMode;
        Camera.Parameters params = this.camera.getParameters();
        switch (this.cameraFlashMode) {
            case 0: params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF); break;
            case 1: params.setFlashMode(Camera.Parameters.FLASH_MODE_ON); break;
            case 2: params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO); break;
        }
        this.camera.setParameters(params);
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

    /* ==================================== CONTROL CAMERA ====================================== */

    public void onStart() {
        this.openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public void onStop() {
        this.stopCamera();
    }

    private void setupCamera() {
        if (this.camera != null) {
            for (Camera.Size previewSize : this.camera.getParameters().getSupportedPreviewSizes()) {
                double currentAspectRatio = (double) previewSize.width / previewSize.height;
                if (Math.abs(currentAspectRatio - ASPECT_RATIO_TARGET) < ASPECT_RATIO_TOLERANCE) {
                    this.cameraPreviewSize = previewSize; break;
                }
            }
            this.surfaceView.requestLayout();
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

    /* ==================================== SURFACE HOLDER ====================================== */

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
            this.surfaceView.requestLayout();
            this.camera.setParameters(parameters);
            this.camera.startPreview();
        }
    }

    /* ================================== PICTURE UTILITY ======================================= */

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        this.surfaceView.stopFocusAnimation(); // hide focus pointer
        this.camera.takePicture(null, null, null, this); // this, null, null, this
    }

    /* =================================== ON TAP FOCUS ========================================= */

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.switchToTouchFocus();
            List<Camera.Area> focusList = new ArrayList<>();
            final Rect focusRect = CameraUtility.getFocusArea(event.getX(), event.getY(), FOCUS_AREA_SIZE, this.surfaceView.getWidth(), this.surfaceView.getHeight());
            focusList.add(new Camera.Area(focusRect, 1000));
            this.setFocusToAreas(focusList);
            this.surfaceView.setFocusPosition(new Point((int) event.getX(), (int) event.getY())); // draw focus pointer
        }
        return true;
    }

    /* =================================== ON FACE FOCUS ======================================== */

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        // set focus to faces
        if (faces.length > 0) {
            List<Camera.Area> focusList = new ArrayList<>();
            for (Camera.Face face : faces)
                focusList.add(new Camera.Area(face.rect, face.score));
            this.setFocusToAreas(focusList);
        }
        this.prepareToDrawDetectedFaces(faces);
    }

    private void prepareToDrawDetectedFaces(Camera.Face[] faces) {
        if (this.cameraDetectedFaces != null) {
            List<Integer> newFacesIds = new ArrayList<>();
            for (int i = 0; i < faces.length; i++) {
                boolean containFace = false;
                for (int j = 0; j < this.cameraDetectedFaces.length; j++)
                    if (faces[i].id == this.cameraDetectedFaces[j].id) {
                        containFace = true; break;
                    }
                if (!containFace)
                    newFacesIds.add(i);
            }
            if (!newFacesIds.isEmpty()) {
                for (Integer faceId : newFacesIds)
                    this.surfaceView.addFacePointers(faces[faceId].id, faces[faceId].rect);
            }
        }
        this.surfaceView.updateFacePointers(faces);
        this.cameraDetectedFaces = faces;
    }

    /* =================================== FOCUS UTILITY ======================================== */

    private void setFocusToAreas(List<Camera.Area> focusList) {
        try {
            Camera.Parameters param = this.camera.getParameters();
            param.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
            param.setFocusAreas(focusList);
            param.setMeteringAreas(focusList);
            this.camera.setParameters(param);
            this.camera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToFaceFocus() {
        this.resetTouchFocus();
        this.setupFaceFocus();
    }

    public void switchToAutoFocus() {
        this.resetFaceFocus();
        this.resetTouchFocus();
        this.setupAutoFocus();
    }

    public void switchToTouchFocus() {
        this.resetFaceFocus();
    }

    // FACE FOCUS
    private void setupFaceFocus() {
        this.camera.startFaceDetection();
    }

    private void resetFaceFocus() {
        this.camera.stopFaceDetection();
        this.surfaceView.clearFacePointers();
    }

    // AUTO FOCUS
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

    // TAP FOCUS
    private void resetTouchFocus() {
        this.surfaceView.stopFocusAnimation();
    }

    /* ===================================== SURFACE ============================================ */

    private void setUpVector() {
        this.surfaceView.setUpVector(-1);
        if (this.inCameraMode(Camera.CameraInfo.CAMERA_FACING_FRONT))
            this.surfaceView.setUpVector(1);
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

    private boolean isFrontCameraSupported() {
        if (Camera.getNumberOfCameras() > 1)
            return true;
        return false;
    }

    private boolean inCameraMode(int cameraId) {
        return (this.cameraId == cameraId ? true : false);
    }

}