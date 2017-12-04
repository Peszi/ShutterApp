package com.pheasant.shutterapp.presenter;

import android.graphics.Bitmap;

import com.pheasant.shutterapp.ui.interfaces.CameraEditorSurfaceInterface;
import com.pheasant.shutterapp.ui.listeners.CameraEditorViewListener;
import com.pheasant.shutterapp.ui.listeners.EditorListener;

/**
 * Created by Peszi on 2017-11-29.
 */

public class CameraEditorPresenter implements CameraEditorViewListener {

    private Bitmap photoBitmap;

    private CameraEditorSurfaceInterface editorInterface;

    private EditorListener editorListener;

    public void setEditorListener(EditorListener editorListener) {
        this.editorListener = editorListener;
    }

    public void setEditorInterface(CameraEditorSurfaceInterface editorInterface) {
        this.editorInterface = editorInterface;
    }

    // UI Callbacks

    @Override
    public void onPageShow() {
        this.editorInterface.setPhotoData(this.photoBitmap);
    }

    @Override
    public void onNewPhoto(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    @Override
    public void onEditModeChange(int index) {
        if (this.editorInterface != null)
            this.editorInterface.setEditMode(index);
    }

    @Override
    public void onPreviewAcceptEvent() {
        if (this.editorListener != null)
            this.editorListener.onPhotoAccepted();
    }

    @Override
    public void onPreviewRejectEvent() {
        if (this.editorListener != null)
            this.editorListener.onPhotoRejected();
    }

    @Override
    public Bitmap getEditedPhoto() {
        if (this.editorInterface != null)
            return this.editorInterface.getEditedPhoto();
        return null;
    }

    @Override
    public void onClose() {
        if (this.editorInterface != null)
            this.editorInterface.getEditedPhoto().recycle();
    }
}
