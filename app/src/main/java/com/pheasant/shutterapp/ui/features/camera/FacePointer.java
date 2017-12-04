package com.pheasant.shutterapp.ui.features.camera;

import android.graphics.Rect;
import android.os.Handler;

/**
 * Created by Peszi on 2017-11-26.
 */

public class FacePointer implements Runnable {

    private final int FACE_POINTER_LIFE_TIME = 200;

    private int faceId;
    private Rect faceRect;

    private Handler handler;

    private boolean toRemove;

    public FacePointer(int faceId, Rect faceRect) {
        this.handler = new Handler();
        this.faceId = faceId;
        this.faceRect = faceRect;
        this.startDrawing();
    }

    private void startDrawing() {
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(this, this.FACE_POINTER_LIFE_TIME);
    }

    public void update(Rect faceRect) {
        this.faceRect = faceRect;
        this.startDrawing();
    }

    @Override
    public void run() {
        this.toRemove = true;
    }

    public Rect getFaceRect() { return this.faceRect; }

    public int getFaceId() {
        return this.faceId;
    }

    public boolean isToRemove() {
        return this.toRemove;
    }
}
