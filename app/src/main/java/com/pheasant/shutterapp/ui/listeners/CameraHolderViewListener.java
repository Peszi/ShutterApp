package com.pheasant.shutterapp.ui.listeners;

/**
 * Created by Peszi on 24.11.2017.
 */

public interface CameraHolderViewListener {
    void onTakePhotoEvent();
    void onSwapCameraEvent();
    void onChangeFlashModeEvent();
    void onFaceFocusEvent();
    void onAutoFocusEvent();
}
