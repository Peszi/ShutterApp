package com.pheasant.shutterapp.ui.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.pheasant.shutterapp.ui.features.camera.FacePointersManager;
import com.pheasant.shutterapp.ui.features.camera.FocusPointer;
import com.pheasant.shutterapp.ui.interfaces.SurfaceBasicInterface;
import com.pheasant.shutterapp.ui.interfaces.CameraSurfaceInterface;
import com.pheasant.shutterapp.ui.listeners.CameraSurfaceListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-05-05.
 */

public class CameraSurface extends SurfaceView implements View.OnTouchListener, CameraSurfaceInterface, SurfaceBasicInterface {

    private Paint paint;

    private FocusPointer focusPointer;
    private FacePointersManager facesManager;

    private int cameraUpVector;
    private int xTouchDown, yTouchDown;

    private CameraSurfaceListener surfaceListener;

    public CameraSurface(Context context, AttributeSet set) {
        super(context, set);
        this.setupSurface();
        this.setupUI();
        this.focusPointer = new FocusPointer(context.getResources());
        this.focusPointer.setSurfaceInterface(this);
        this.facesManager = new FacePointersManager(context.getResources());
        this.facesManager.setSurfaceInterface(this);
    }

    private void setupSurface() {
        this.setOnTouchListener(this);
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(false);
    }

    private void setupUI() {
        this.paint = new Paint();
        this.paint.setTextSize(40);
        this.paint.setColor(Color.WHITE);
        this.cameraUpVector = -1;
    }

    public void setSurfaceListener(CameraSurfaceListener surfaceListener) {
        this.surfaceListener = surfaceListener;
    }

    @Override
    public void initFacePointers() {
        this.facesManager.init();
    }

    @Override
    public void drawFocusPointer(int x, int y) {
        this.focusPointer.showPointer(x, y);
    }

    @Override
    public void drawFacesPointers(ArrayList<Camera.Face> detectedFaces) {
        this.facesManager.updateFaces(detectedFaces);
    }

    @Override
    public void setCamera(int cameraId) {
        if (cameraId == 0) { this.cameraUpVector = -1; }
        else { this.cameraUpVector = 1; }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int xPoint = (int) event.getX(); final int yPoint = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) { // Store Touch Down Cords
            this.xTouchDown = xPoint; this.yTouchDown = yPoint;
        }
        if (event.getAction() == MotionEvent.ACTION_UP && this.isTouchPointClose(xPoint, yPoint, 50)) // TODO margin value calc
            this.makeTouchCallback(xPoint, yPoint);
        return true;
    }

    private boolean isTouchPointClose(int x, int y, int margin) {
        if (Math.abs(this.xTouchDown - x) < margin && Math.abs(this.yTouchDown - y) < margin)
            return true;
        return false;
    }

    private void makeTouchCallback(int xPoint, int yPoint) {
        final int xFixed = CameraUtility.getFixedPointX(yPoint, this.getHeight());
        final int yFixed = CameraUtility.getFixedPointY(xPoint, this.getWidth());
        if (this.surfaceListener != null)
            this.surfaceListener.onTouchDownEvent(xPoint, yPoint, xFixed, yFixed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.focusPointer.drawPointer(canvas);
        this.facesManager.drawFacesPointers(canvas, this.paint);
    }

    @Override
    public void invalidateCanvas() {
        this.invalidate();
    }

    @Override
    public Rect getScreenRectFromFace(Rect cameraRect) {
        final float faceRectScale = 1.25f;
        final int faceSize = (int) (cameraRect.height() / 2000f * this.getWidth() * faceRectScale);
        final Point point = CameraUtility.getScreenPoint(new Point(cameraRect.centerY(), cameraRect.centerX() * this.cameraUpVector), this.getWidth(), this.getHeight());
        Rect rect = new Rect(-faceSize/2, -faceSize/2, faceSize/2, faceSize/2);
        rect.offset(point.x, point.y);
        return rect;
    }


}