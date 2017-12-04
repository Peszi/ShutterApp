package com.pheasant.shutterapp.ui.features.camera;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.ui.interfaces.SurfaceBasicInterface;

/**
 * Created by Peszi on 2017-11-25.
 */

public class FocusPointer implements Runnable {

    private final int POINTER_SIZE = 60;
    private final int POINTER_LIFE_TIME = 1000;
    private final float POINTER_DELTA = 1/20f;
    private final float POINTER_SIZE_CHANGE = 0.2f;

    private int pointerX, pointerY;
    private RectF pointerRect;
    private Paint pointerPaint;
    private Bitmap pointerBitmap;
    private int pointerSize;

    private boolean isVisible;
    private float time;

    private Handler handler;
    private SurfaceBasicInterface surfaceInterface;

    public FocusPointer(Resources resources) {
        this.handler = new Handler();
        this.pointerBitmap = BitmapFactory.decodeResource(resources, R.drawable.focus_pointer);
        this.pointerSize = (int) (this.POINTER_SIZE * resources.getDisplayMetrics().density);
        this.pointerRect = new RectF();
        this.setBitmapSize(1);
        this.pointerPaint = new Paint();
    }

    public void setSurfaceInterface(SurfaceBasicInterface surfaceInterface) {
        this.surfaceInterface = surfaceInterface;
    }

    private void setBitmapSize(final float scale) {
        final int currentPointerSize = (int) (pointerSize/2 * scale);
        this.pointerRect.set(-currentPointerSize, -currentPointerSize, currentPointerSize, currentPointerSize);
    }

    public void showPointer(int x, int y) {
        this.startDrawing();
        this.translatePointer(x, y);
        if (this.surfaceInterface != null)
            this.surfaceInterface.invalidateCanvas();
    }

    private void translatePointer(int x, int y) {
        this.pointerX = x; this.pointerY = y;
    }

    private void startDrawing() {
        this.isVisible = true;
        this.time = 0;
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(this, this.POINTER_LIFE_TIME);
    }

    public void drawPointer(Canvas canvas) {
        if (this.isVisible) {
            this.time += this.POINTER_DELTA;
            this.setBitmapSize((float) (1 + Math.sin(this.time) * this.POINTER_SIZE_CHANGE)); // Animate Size of Pointer
            canvas.translate(this.pointerX, this.pointerY);
            canvas.drawBitmap(this.pointerBitmap, new Rect(0, 0, this.pointerBitmap.getWidth(), this.pointerBitmap.getHeight()), this.pointerRect, this.pointerPaint);
            canvas.translate(-this.pointerX, -this.pointerY);
            if (this.surfaceInterface != null)
                this.surfaceInterface.invalidateCanvas();
        }
    }

    @Override
    public void run() {
        this.isVisible = false;
    }
}
