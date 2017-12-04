package com.pheasant.shutterapp.ui.interfaces;

/**
 * Created by Peszi on 24.11.2017.
 */

public interface CameraHolderView {
    void startTakePhotoAnimation();
    void changeCameraSwapIcon(int iconIdx);
    void changeFlashModeIcon(int iconIdx);
    void showFaceFocusIcon(boolean show);
    void showAutoFocusIcon(boolean show);
}
