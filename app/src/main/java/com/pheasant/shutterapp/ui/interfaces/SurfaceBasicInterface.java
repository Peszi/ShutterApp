package com.pheasant.shutterapp.ui.interfaces;

import android.graphics.Rect;

/**
 * Created by Peszi on 2017-11-25.
 */

public interface SurfaceBasicInterface {
    void invalidateCanvas();
    Rect getScreenRectFromFace(Rect cameraRect);
}
