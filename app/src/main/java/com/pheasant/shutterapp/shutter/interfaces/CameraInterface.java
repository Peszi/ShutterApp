package com.pheasant.shutterapp.shutter.interfaces;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraInterface {
    void changeCamera(int cameraId);
    void changeFlashMode(int flashMode);
    void takePhoto();
    void setOnFaceFocus();
    void setAutoFocus();
    void setPointFocus();
}
