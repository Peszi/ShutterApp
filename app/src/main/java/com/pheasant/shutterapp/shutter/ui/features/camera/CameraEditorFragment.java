package com.pheasant.shutterapp.shutter.ui.features.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.features.camera.editor.utils.EditorModeController;
import com.pheasant.shutterapp.shutter.presenter.CameraEditorPresenter;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraEditorInterface;
import com.pheasant.shutterapp.shutter.ui.listeners.EditorListener;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-27.
 */

public class CameraEditorFragment extends Fragment implements View.OnClickListener, EditorModeController.ChangeModeListener, CameraEditorInterface {

    private EditorModeController modeController;
    private EditorSurfaceSurface editorSurface;

    private CameraEditorPresenter editorPresenter;

    public CameraEditorFragment() {
        this.editorPresenter = new CameraEditorPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_editor, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.setupUI(view);

        this.editorSurface = (EditorSurfaceSurface) view.findViewById(R.id.editor_surface);
        this.editorSurface.setupEditors(this.getContext(), view);

        this.editorPresenter.setEditorInterface(this.editorSurface);
        this.editorPresenter.onPageShow();

        return view;
    }

    private void setupUI(View view) {
        this.modeController = new EditorModeController(view);
        this.modeController.initButtons();
        this.modeController.setChangeModeListener(this);
        view.findViewById(R.id.preview_accept).setOnClickListener(this);
        view.findViewById(R.id.preview_reject).setOnClickListener(this);
    }

    public void setEditorListener(EditorListener editorListener) {
        this.editorPresenter.setEditorListener(editorListener);
    }

    // UI callbacks

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preview_accept: this.editorPresenter.onPreviewAcceptEvent(); break;
            case R.id.preview_reject: this.editorPresenter.onPreviewRejectEvent(); break;
        }
    }

    @Override
    public void onModeChange(int index) {
        this.editorPresenter.onEditModeChange(index);
    }

    // Editor Interface

    @Override
    public void setNewPhoto(Bitmap photoBitmap) {
        this.editorPresenter.onNewPhoto(photoBitmap);
    }

    @Override
    public Bitmap getEditedPhoto() {
        return this.editorPresenter.getEditedPhoto();
    }
}
