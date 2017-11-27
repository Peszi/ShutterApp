package com.pheasant.shutterapp.shutter.presenter;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraManageView;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraPreviewView;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraEditorListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraManageViewListener;

/**
 * Created by Peszi on 2017-11-24.
 */

public class ManageCameraPresenter implements CameraManageViewListener, CameraListener, CameraEditorListener {

    private ShutterApiInterface shutterApiInterface;
    private LockingViewPager pagerInterface;

    private CameraManageView cameraManageView;

    private boolean canLogout;

    public ManageCameraPresenter() {}

    // Setters

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
    }

    public void setPagerInterface(LockingViewPager pagerInterface) {
        this.pagerInterface = pagerInterface;
    }

    public void setManageCameraView(CameraManageView cameraManageView) {
        this.cameraManageView = cameraManageView;
    }

    // View Callbacks

    @Override
    public void onPageShow() {
        this.setCameraMode();
    }

    @Override
    public boolean onBackBtn() {
        if (!this.canLogout) {
            this.setCameraMode();
            return false;
        }
        return this.canLogout;
    }

    // Camera Callbacks

    @Override
    public void onPhotoEvent(Bitmap cameraPhoto) {
        this.setEditorMode(cameraPhoto);
    }

    @Override
    public void onCameraErrorMessageEvent(String message) {

    }

    // Editor Callbacks

    @Override
    public void onPhotoEdited(Bitmap finalBitmap) {

    }

    @Override
    public void onPhotoRejected() {
        this.setCameraMode();
    }

    // Other

    private void setCameraMode() {
        this.pagerInterface.setEnabled(true);
        this.cameraManageView.setCameraMode();
        this.canLogout = true;
    }

    private void setEditorMode(Bitmap cameraPhoto) {
        this.pagerInterface.setEnabled(false);
        this.cameraManageView.setEditorMode(cameraPhoto);
        this.canLogout = false;
    }
}
