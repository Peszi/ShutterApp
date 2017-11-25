package com.pheasant.shutterapp.shutter.ui.interfaces;

/**
 * Created by Peszi on 24.11.2017.
 */

public interface CameraPreviewView {
    void startTakePhotoAnimation();
    void changeCameraSwapIcon(int iconIdx);
    void changeFlashModeIcon(int iconIdx);
    void showFaceFocusIcon(boolean show);
    void showAutoFocusIcon(boolean show);
}
