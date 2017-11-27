package com.pheasant.shutterapp.shutter.ui.interfaces;

import android.hardware.Camera;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-24.
 */

public interface CameraSurfaceInterface {
    void initFacePointers();
    void drawFocusPointer(int x, int y);
    void drawFacesPointers(ArrayList<Camera.Face> detectedFaces);
    void setCamera(int cameraId);
}
