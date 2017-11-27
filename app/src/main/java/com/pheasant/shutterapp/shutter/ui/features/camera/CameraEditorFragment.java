package com.pheasant.shutterapp.shutter.ui.features.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.presenter.ManageCameraPresenter;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraEditorListener;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-27.
 */

public class CameraEditorFragment extends Fragment implements View.OnClickListener {

    private ImageView cameraPhoto;
    private Bitmap photo;

    private CameraEditorListener editorListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_editor, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        view.findViewById(R.id.preview_accept).setOnClickListener(this);
        view.findViewById(R.id.preview_reject).setOnClickListener(this);

        this.cameraPhoto = (ImageView) view.findViewById(R.id.editor_photo);
        this.cameraPhoto.setImageBitmap(photo);
        return view;
    }

    public void setEditorListener(CameraEditorListener editorListener) {
        this.editorListener = editorListener;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preview_accept:
                this.editorListener.onPhotoEdited(this.photo); break;
            case R.id.preview_reject:
                this.editorListener.onPhotoRejected(); break;
        }
    }
}
