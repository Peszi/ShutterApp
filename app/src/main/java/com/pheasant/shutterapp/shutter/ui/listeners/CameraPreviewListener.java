package com.pheasant.shutterapp.shutter.ui.listeners;

/**
 * Created by Peszi on 24.11.2017.
 */

public interface CameraPreviewListener {
    void onTakePhotoEvent();
    void onSwapCameraEvent();
    void onChangeFlashModeEvent();
    void onFaceFocusEvent();
    void onAutoFocusEvent();
}
