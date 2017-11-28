package com.pheasant.shutterapp.features.shutter.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.features.CameraFragment;

/**
 * Created by Peszi on 2017-05-03.
 */

public class EditorManager {

    private EditorSurface editorSurface;
    private View view;
    private boolean isEditing;

    public EditorManager(Context context, View view, CameraFragment cameraFragment) {
        this.view = view;
        this.switchToCamera();
        this.setupSurface(context, view);
        this.setupPreviewButtons(view);
    }

    private void setupSurface(Context context, View view) {
        this.editorSurface = new EditorSurface(context, view);
        ((RelativeLayout) view.findViewById(R.id.layout_preview)).addView(this.editorSurface, 0);
    }

    private void setupPreviewButtons(View view) {
//        view.findViewById(R.id.preview_accept).setOnClickListener(this);
//        view.findViewById(R.id.preview_reject).setOnClickListener(this);
    }

    public void startEditing(final Bitmap photo) {
        this.isEditing = true;
        this.editorSurface.onStart(photo);
        this.switchToPhoto();
    }

    public void stopEditing() {
        this.isEditing = false;
        this.editorSurface.onStop();
        this.switchToCamera();
    }

    private void switchToPhoto() {
//        this.view.findViewById(R.id.layout_preview).setVisibility(View.VISIBLE);
//        this.view.findViewById(R.id.layout_camera).setVisibility(View.GONE);
    }

    private void switchToCamera() {
//        this.view.findViewById(R.id.layout_camera).setVisibility(View.VISIBLE);
//        this.view.findViewById(R.id.layout_preview).setVisibility(View.GONE);
    }

    public Bitmap getEditedPhoto() {
        return this.editorSurface.getBitmap();
    }

    public boolean isEditing() {
        return this.isEditing;
    }
}
