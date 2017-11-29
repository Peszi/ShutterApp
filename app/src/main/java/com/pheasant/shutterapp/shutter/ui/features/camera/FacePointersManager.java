package com.pheasant.shutterapp.shutter.ui.features.camera;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.interfaces.SurfaceBasicInterface;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-26.
 */

public class FacePointersManager implements Runnable {

    private final int SHOW_POINTERS_TIME = 2000;

    private Handler handler;
    private boolean hidePointers;
    private ArrayList<FacePointer> facesPointers;

    private Bitmap facePointerBitmap;

    private SurfaceBasicInterface surfaceInterface;

    public FacePointersManager(Resources resources) {
        this.handler = new Handler();
        this.facesPointers = new ArrayList<>();
        this.facePointerBitmap = BitmapFactory.decodeResource(resources, R.drawable.focus_pointer);
    }

    public void setSurfaceInterface(SurfaceBasicInterface surfaceInterface) {
        this.surfaceInterface = surfaceInterface;
    }

    public void init() {
        this.facesPointers.clear();
    }

    public void updateFaces(ArrayList<Camera.Face> detectedFaces) {
        for (Camera.Face detectedFace : detectedFaces) {
            boolean isExisting = false;
            for (FacePointer facePointer : this.facesPointers) {
                if (detectedFace.id == facePointer.getFaceId()) {
                    facePointer.update(this.surfaceInterface.getScreenRectFromFace(detectedFace.rect));
                    isExisting = true;
                }
            }
            if (!isExisting) {
                this.facesPointers.add(new FacePointer(detectedFace.id, this.surfaceInterface.getScreenRectFromFace(detectedFace.rect)));
                this.startTimerToHide();
            }
        }
        this.surfaceInterface.invalidateCanvas();
    }

    private void startTimerToHide() {
        this.hidePointers = false;
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(this, this.SHOW_POINTERS_TIME);
    }

    public void drawFacesPointers(Canvas canvas, Paint paint) {
        if (!this.facesPointers.isEmpty()) {
            this.removeWastedPointers();
            if (!this.hidePointers)
                this.drawPointers(canvas, paint);
        }
    }

    private void removeWastedPointers() {
        ArrayList<FacePointer> removeList = new ArrayList<>();
        for (int i = 0; i < this.facesPointers.size(); i++)
            if (this.facesPointers.get(i).isToRemove())
                removeList.add(this.facesPointers.get(i));
        for (FacePointer facePointer : removeList)
            this.facesPointers.remove(facePointer);
    }

    private void drawPointers(Canvas canvas, Paint paint) {
        for (FacePointer facePointer : this.facesPointers) {
            canvas.drawBitmap(this.facePointerBitmap, new Rect(0, 0, this.facePointerBitmap.getWidth(), this.facePointerBitmap.getHeight()), facePointer.getFaceRect(), paint);
            // canvas.drawText("ID " + facePointer.getFaceId(), facePointer.getFaceRect().right, facePointer.getFaceRect().bottom, paint);
        }
        this.surfaceInterface.invalidateCanvas();
    }

    @Override
    public void run() {
        this.hidePointers = true;
    }

}
