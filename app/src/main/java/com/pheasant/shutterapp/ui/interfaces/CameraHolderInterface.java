package com.pheasant.shutterapp.ui.interfaces;

import com.pheasant.shutterapp.ui.camera.CameraFocus;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraHolderInterface {
    void changeCamera(int cameraId);
    void changeFlashMode(int flashMode);
    void changeFocusMode(CameraFocus focusMode);
    void setFocusPoint(int fixedX, int fixedY, int areaSize, int areaWeight);
    void takePhoto();
}
