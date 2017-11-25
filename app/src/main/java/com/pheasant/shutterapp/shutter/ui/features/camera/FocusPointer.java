package com.pheasant.shutterapp.shutter.ui.features.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.pheasant.shutterapp.shutter.camera.CameraSurface;

/**
 * Created by Peszi on 2017-11-25.
 */

public class FocusPointer implements Runnable {

    private final int POINTER_SIZE = 80;
    private final int POINTER_ALPHA = 196;
    private final int POINTER_LIFE_TIME = 2000;

    private RectF pointerRect;
    private Paint pointerPaint;
    private float pointerScale = 1;

    private boolean isVisible;

    private Handler handler;
    private SurfaceBasicInterface surfaceInterface;

    public FocusPointer(DisplayMetrics displayMetrics) {
        final int pointerSize = (int) (this.POINTER_SIZE * displayMetrics.density);
        this.pointerRect = new RectF(-pointerSize/2, -pointerSize/2, pointerSize/2, pointerSize/2);
        this.pointerPaint = new Paint();
        this.pointerPaint.setColor(Color.WHITE); //Color.argb(this.POINTER_ALPHA, 1, 1, 1));
    }

    public void setSurfaceInterface(SurfaceBasicInterface surfaceInterface) {
        this.surfaceInterface = surfaceInterface;
    }

    public void showPointer(int x, int y) {
        Log.d("RESPONSE", "X " + x + " Y " + y);
        this.startTimer();
        this.isVisible = true;
        this.translatePointer(x, y);
        if (this.surfaceInterface != null)
            this.surfaceInterface.invalidateCanvas();
    }

    private void translatePointer(int x, int y) {
        this.pointerRect.offsetTo(x - this.pointerRect.width()/2, y - this.pointerRect.height()/2);
    }

    private void startTimer() {
        this.handler = new Handler();
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(this, this.POINTER_LIFE_TIME);
    }

    public void drawPointer(Canvas canvas) {
        if (this.isVisible) {
//            canvas.scale(this.pointerScale, this.pointerScale);
            canvas.drawOval(this.pointerRect, this.pointerPaint);
//            canvas.scale(1 - this.pointerScale, 1 - this.pointerScale);
            if (this.surfaceInterface != null)
                this.surfaceInterface.invalidateCanvas();
        }
    }

    @Override
    public void run() {
        Log.d("RESPONSE", "HIDE POINTER");
        this.isVisible = false;
    }
}
