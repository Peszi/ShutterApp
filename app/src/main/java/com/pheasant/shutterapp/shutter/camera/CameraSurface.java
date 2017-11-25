package com.pheasant.shutterapp.shutter.camera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.features.camera.FocusPointer;
import com.pheasant.shutterapp.shutter.ui.features.camera.SurfaceBasicInterface;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraSurfaceInterface;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraSurfaceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class CameraSurface extends SurfaceView implements View.OnTouchListener, CameraSurfaceInterface, SurfaceBasicInterface {

    private final int FOCUS_POINTER_SIZE = 100; // px
    private final int FOCUS_POINTER_SIZE_CHANGE = 10; // px
    private final long FOCUS_ANIM_TIME = 1500;
    private final float FOCUS_ANIM_SPEED = 0.0075f;

    private Point focusPosition;
    private Bitmap focusBitmap;
    private boolean focusAnimation;
    private int focusSize;
    private long focusLastTime;
    private long focusCurrentTime;

    private List<FocusFace> facePointers;
    private Paint facePointerPaint;
    private Paint facePointerBackPaint;

    private int cameraUpVector;

    private FocusPointer focusPointer;

    private CameraSurfaceListener surfaceListener;

    public CameraSurface(Context context, AttributeSet set) {
        super(context, set);
        this.setupSurface();
        this.setupUI(context.getResources());
        this.focusPointer = new FocusPointer(context.getResources().getDisplayMetrics());
        this.focusPointer.setSurfaceInterface(this);
    }

    private void setupSurface() {
        this.setOnTouchListener(this);
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(false);
    }

    private void setupUI(Resources resources) {
        this.focusBitmap = BitmapFactory.decodeResource(resources, R.drawable.focus_area);
    }

    public void setSurfaceListener(CameraSurfaceListener surfaceListener) {
        this.surfaceListener = surfaceListener;
    }

    @Override
    public void drawFocusPointer(int x, int y) {
        this.focusPointer.showPointer(x, y);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && this.surfaceListener != null) {
            final int xPoint = (int) event.getX();
            final int yPoint = (int) event.getY();
            final int xFixed = CameraUtility.getFixedPointX(yPoint, this.getHeight());
            final int yFixed = CameraUtility.getFixedPointY(xPoint, this.getWidth());
            this.surfaceListener.onTouchDownEvent(xPoint, yPoint, xFixed, yFixed);
//            this.surfaceView.setFocusPosition(new Point((int) event.getX(), (int) event.getY())); // draw focus pointer
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.focusPointer.drawPointer(canvas);
    }

    @Override
    public void invalidateCanvas() {
        this.invalidate();
    }

    @Override
    public int getSurfaceWidth() {
        return this.getWidth();
    }

    @Override
    public int getSurfaceHeight() {
        return this.getHeight();
    }

    /* ===================================== TAP FOCUS ========================================== */

    private void setupFocusPointer(Context context) {
        this.focusBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.focus_area);
    }

    protected void setFocusPosition(Point focusPosition) {
        this.focusPosition = focusPosition;
        this.playFocusAnimation();
    }

    private void playFocusAnimation() {
        this.focusCurrentTime = 0;
        this.focusLastTime = System.currentTimeMillis();
        this.focusAnimation = true;
        this.invalidate();
    }

    protected void stopFocusAnimation() {
        this.focusCurrentTime = 0;
        this.focusAnimation = false;
    }

    private void drawFocusPointer(Canvas canvas) {
        if (this.focusAnimation) {
            // time
            this.focusCurrentTime += System.currentTimeMillis() - this.focusLastTime;
            this.focusLastTime = System.currentTimeMillis();
            if (this.focusCurrentTime > FOCUS_ANIM_TIME)
                this.stopFocusAnimation();
            this.focusSize = (int) (Math.sin(this.focusCurrentTime * this.FOCUS_ANIM_SPEED) * this.FOCUS_POINTER_SIZE_CHANGE);
            final int focusSize = this.FOCUS_POINTER_SIZE + this.focusSize;
            // draw
            final Rect focusRect = new Rect(this.focusPosition.x - focusSize, this.focusPosition.y - focusSize, this.focusPosition.x + focusSize, this.focusPosition.y + focusSize);
            canvas.drawBitmap(this.focusBitmap, new Rect(0, 0, this.focusBitmap.getWidth(), this.focusBitmap.getHeight()), focusRect, this.facePointerPaint);
            this.invalidate();
        }
    }

    /* ===================================== FACE FOCUS ========================================= */

    private void prepareFacePointers() {
        this.facePointers = new ArrayList<>();
        // inner paint
        final DashPathEffect pathEffect = new DashPathEffect(new float[] {20, 30}, 0);
        this.facePointerPaint = new Paint();
        this.facePointerPaint.setStyle(Paint.Style.STROKE);
        this.facePointerPaint.setPathEffect(pathEffect);
        this.facePointerPaint.setStrokeWidth(6);
        this.facePointerPaint.setColor(Color.WHITE);
        // outer paint
        this.facePointerBackPaint = new Paint();
        this.facePointerBackPaint.setStyle(Paint.Style.STROKE);
        this.facePointerBackPaint.setPathEffect(pathEffect);
        this.facePointerBackPaint.setStrokeWidth(10);
        this.facePointerBackPaint.setColor(Color.BLACK);
        this.facePointerBackPaint.setAlpha(128);
    }

    protected void addFacePointers(int id, Rect rect) {
        this.facePointers.add(new FocusFace(id, rect));
        this.invalidate();
    }

    protected void clearFacePointers() {
        this.facePointers.clear();
        this.invalidate();
    }

    protected void updateFacePointers(Camera.Face[] cameraFaces) {
        if (cameraFaces.length > 0) {
            FocusFace focusFaceToRemove = null;
            for (FocusFace focusFace : this.facePointers) {
                boolean stillInCamera = false;
                for (Camera.Face face : cameraFaces) {
                    if (focusFace.faceId == face.id) {
                        focusFace.faceRect = face.rect;
                        stillInCamera = true;
                    }
                }
                if (!stillInCamera)
                    focusFaceToRemove = focusFace;
            }
            if (focusFaceToRemove != null)
                this.facePointers.remove(focusFaceToRemove);
        } else {
            this.clearFacePointers();
        }
    }

    private void drawFacePointers(Canvas canvas) {
        if (!this.facePointers.isEmpty()) {
            FocusFace focusFaceToRemove = null;
            for (FocusFace focusFace : this.facePointers) {
                final RectF faceRect = new RectF(this.toScreenRect(focusFace.faceRect));
                canvas.drawOval(faceRect, this.facePointerBackPaint);
                canvas.drawOval(faceRect, this.facePointerPaint);
                if (focusFace.toRemove)
                    focusFaceToRemove= focusFace;
            }
            if (focusFaceToRemove != null)
                this.facePointers.remove(focusFaceToRemove);
            this.invalidate();
        }
    }

    protected void setUpVector(int upVector) {
        this.cameraUpVector = upVector;
    }

    private Rect toScreenRect(Rect cameraRect) {
        final float faceRectScale = 1.25f;
        final int faceSize = (int) (cameraRect.height() / 2000f * this.getWidth() * faceRectScale);
        final Point point = CameraUtility.getScreenPoint(new Point(cameraRect.centerY(), cameraRect.centerX() * this.cameraUpVector), this.getWidth(), this.getHeight());
        Rect rect = new Rect(-faceSize/2, -faceSize/2, faceSize/2, faceSize/2);
        rect.offset(point.x, point.y);
        return rect;
    }

    /* ======================================== DRAW ============================================ */

    private class FocusFace {

        private final int LIFE_TIME = 2000;

        private int faceId;
        private Rect faceRect;
        private boolean toRemove;

        private FocusFace(int id, Rect rect) {
            this.faceId = id;
            this.faceRect = rect;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toRemove = true;
                }
            }, this.LIFE_TIME);
        }
    }
}