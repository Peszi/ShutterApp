package com.pheasant.shutterapp.shutter.ui.interfaces;

import android.hardware.Camera;

import com.pheasant.shutterapp.shutter.camera.CameraFocus;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraInterface {
    void changeCamera(int cameraId);
    void changeFlashMode(int flashMode);
    void changeFocusMode(CameraFocus focusMode);
    void setFocusPoint(int fixedX, int fixedY, int areaSize, int areaWeight);
    void takePhoto();
}
