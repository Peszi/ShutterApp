package com.pheasant.shutterapp.ui.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;

/**
 * Created by Peszi on 2017-05-03.
 */

public class CameraUtility {

    public static int getFixedPointX(float y, int surfaceHeight) {
        return (int) ((0.5f - y / surfaceHeight) * -2000);
    }

    public static int getFixedPointY(float x, int surfaceWidth) {
        return (int) ((0.5f - x / surfaceWidth) * 2000);
    }

    public static Camera.Area getFocusArea(int fixedX, int fixedY, int area, int weight) {
        Rect focusArea = new Rect(-area, -area, area, area);
        focusArea.offset(fixedX, fixedY); // translate and clamp to focus range (-1000, 1000)
        focusArea.left = Math.max(-1000, focusArea.left);
        focusArea.bottom = Math.min(1000, focusArea.bottom);
        focusArea.right = Math.min(1000, focusArea.right);
        focusArea.top = Math.max(-1000, focusArea.top);
        return new Camera.Area(focusArea, weight);
    }

    public static Rect getFocusArea(final float x, final float y, int area, int surfaceWidth, int surfaceHeight) {
        final int fixedX = (int) ((0.5f - y / surfaceHeight) * -2000);
        final int fixedY = (int) ((0.5f - x / surfaceWidth) * 2000);
        Rect focusArea = new Rect(-area, -area, area, area);
        // translate and clamp to focus range (-1000, 1000)
        focusArea.offset(fixedX, fixedY);
        focusArea.left = Math.max(-1000, focusArea.left);
        focusArea.bottom = Math.min(1000, focusArea.bottom);
        focusArea.right = Math.min(1000, focusArea.right);
        focusArea.top = Math.max(-1000, focusArea.top);
        return focusArea;
    }

    public static Point getScreenPoint(Point cameraPoint, int surfaceWidth, int surfaceHeight) {
        return new Point((int) (surfaceWidth/2 - (float) (cameraPoint.x) / 1000 * surfaceWidth/2), (int) (surfaceHeight/2 - (float) (cameraPoint.y) / 1000 * surfaceHeight/2));
    }

    public static Bitmap getBitmapFromArray(final byte[] photoData, final int cameraId) {
        Bitmap resultBitmap = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
        final Matrix transformMatrix = new Matrix();
        transformMatrix.postRotate(90);
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT)
            transformMatrix.postScale(1, -1); // flip front camera picture vertical
        resultBitmap = Bitmap.createBitmap(resultBitmap, 0, 0, resultBitmap.getWidth(), resultBitmap.getHeight(), transformMatrix, true);
        return resultBitmap;
    }

}
