package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;

/**
 * Created by Peszi on 2017-11-24.
 */

public class ManageCameraPresenter {

    private ShutterApiInterface shutterApiInterface;

    public ManageCameraPresenter() {}

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
    }
}
