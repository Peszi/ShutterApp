package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraPreviewView;

/**
 * Created by Peszi on 2017-11-24.
 */

public class ManageCameraPresenter {

    private ShutterApiInterface shutterApiInterface;

    private CameraPreviewView cameraView;

    public ManageCameraPresenter() {}

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
    }

    public void setCameraView(CameraPreviewView cameraView) {
        this.cameraView = cameraView;
    }
}
