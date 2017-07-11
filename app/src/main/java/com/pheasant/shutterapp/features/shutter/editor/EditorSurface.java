package com.pheasant.shutterapp.features.shutter.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.pheasant.shutterapp.features.shutter.editor.editors.DrawingEditor;
import com.pheasant.shutterapp.features.shutter.editor.editors.MessageEditor;
import com.pheasant.shutterapp.features.shutter.editor.editors.PhotoEditor;
import com.pheasant.shutterapp.features.shutter.editor.utils.DrawListener;
import com.pheasant.shutterapp.features.shutter.editor.utils.EditorModeController;

/**
 * Created by Peszi on 2017-05-10.
 */

public class EditorSurface extends SurfaceView implements View.OnTouchListener, DrawListener, EditorModeController.ChangeModeListener {

    private EditorModeController editButtonsController;

    private PhotoEditor photoEditor;
    private DrawingEditor drawingEditor;
    private MessageEditor messageEditor;

    public EditorSurface(Context context, View view) {
        super(context);
        this.setupSurface();
        this.setupEditors(context, view);
    }

    private void setupSurface() {
        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.setOnTouchListener(this);
    }

    private void setupEditors(Context context, View view) {
        this.editButtonsController = new EditorModeController(view);
        this.editButtonsController.setChangeModeListener(this);
        this.photoEditor = new PhotoEditor();
        this.drawingEditor = new DrawingEditor(context, view, this);
        this.messageEditor = new MessageEditor(context, view, this);
    }

    public void onStart(Bitmap photo) {
        this.editButtonsController.initButtons();
        this.photoEditor.init(photo);
        this.drawingEditor.init();
        this.messageEditor.init(this.getRootView().getHeight()/2);
    }

    public void onStop() {
        this.messageEditor.clear();
        this.destroyDrawingCache();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.drawingEditor.onTouch(view, event);
        this.messageEditor.onTouch(view, event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.photoEditor.drawPhoto(canvas);
        this.drawingEditor.drawDrawings(canvas);
        this.messageEditor.drawMessage(canvas);
    }

    // Modes

    @Override
    public void onFaceMode() {
        this.drawingEditor.startDrawing(false);
        this.messageEditor.disableEditing(true);
    }

    @Override
    public void onDrawMode() {
        this.drawingEditor.startDrawing(true);
        this.messageEditor.disableEditing(true);
    }

    @Override
    public void onNoMode() {
        this.drawingEditor.startDrawing(false);
        this.messageEditor.disableEditing(false);
    }

    @Override
    public void drawRequest() {
        this.invalidate();
    }

    public Bitmap getBitmap() {
        return Bitmap.createScaledBitmap(this.getDrawingCache(), this.getDrawingCache().getWidth(), this.getDrawingCache().getHeight(), false);
    }
}