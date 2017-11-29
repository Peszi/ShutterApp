package com.pheasant.shutterapp.shutter.ui.interfaces;

import android.graphics.Bitmap;

/**
 * Created by Peszi on 2017-11-29.
 */

public interface CameraEditorInterface {
    void setNewPhoto(Bitmap photoBitmap);
    Bitmap getEditedPhoto();
}
