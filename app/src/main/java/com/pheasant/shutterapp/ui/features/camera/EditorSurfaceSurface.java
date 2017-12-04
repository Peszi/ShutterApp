package com.pheasant.shutterapp.ui.features.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.pheasant.shutterapp.ui.features.camera.editor.DrawingEditor;
import com.pheasant.shutterapp.ui.features.camera.editor.MessageEditor;
import com.pheasant.shutterapp.ui.features.camera.editor.PhotoDrawer;
import com.pheasant.shutterapp.ui.features.camera.editor.utils.DrawListener;
import com.pheasant.shutterapp.ui.interfaces.CameraEditorSurfaceInterface;

/**
 * Created by Peszi on 2017-05-10.
 */

public class EditorSurfaceSurface extends SurfaceView implements CameraEditorSurfaceInterface, View.OnTouchListener, DrawListener {

    private PhotoDrawer photoDrawer;
    private DrawingEditor drawingEditor;
    private MessageEditor messageEditor;

    public EditorSurfaceSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.setOnTouchListener(this);
        this.photoDrawer = new PhotoDrawer();
    }

    public void setupEditors(Context context, View view) {
        this.drawingEditor = new DrawingEditor(context, view, this);
        this.messageEditor = new MessageEditor(context, view, this);
    }

    @Override
    public void setPhotoData(Bitmap photoData) {
        this.photoDrawer.init(photoData);
        this.drawingEditor.init();
        this.messageEditor.init(this.getRootView().getHeight()/2);
    }

    @Override
    public void cleanPhotoCache() {
        this.messageEditor.clear();
        this.destroyDrawingCache();
    }

    @Override
    public void setEditMode(int index) {
        switch (index) {
            case 0: this.onNoEditor(); break;
            case 1: this.onDrawEditor(); break;
            case 2: this.onFaceEditor(); break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.drawingEditor.onTouch(view, event);
        this.messageEditor.onTouch(view, event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.photoDrawer.drawPhoto(canvas);
        this.drawingEditor.drawDrawings(canvas);
        this.messageEditor.drawMessage(canvas);
    }

    @Override
    public void invalidateSurface() {
        this.invalidate();
    }

    private void onNoEditor() {
        this.drawingEditor.startDrawing(false);
        this.messageEditor.disableEditing(false);
    }

    private void onDrawEditor() {
        this.drawingEditor.startDrawing(true);
        this.messageEditor.disableEditing(true);
    }

    private void onFaceEditor() {
        this.drawingEditor.startDrawing(false);
        this.messageEditor.disableEditing(true);
    }

    @Override
    public Bitmap getEditedPhoto() {
        return Bitmap.createScaledBitmap(this.getDrawingCache(), this.getDrawingCache().getWidth(), this.getDrawingCache().getHeight(), false);
    }
}