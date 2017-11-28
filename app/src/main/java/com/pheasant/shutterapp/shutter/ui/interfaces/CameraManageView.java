package com.pheasant.shutterapp.shutter.ui.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.shutter.api.data.FriendData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-27.
 */

public interface CameraManageView {
    void setCameraMode();
    void setEditorMode(Bitmap cameraPhoto);
}
