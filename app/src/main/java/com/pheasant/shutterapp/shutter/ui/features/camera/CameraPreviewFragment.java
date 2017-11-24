package com.pheasant.shutterapp.shutter.ui.features.camera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.camera.CameraHolder;
import com.pheasant.shutterapp.shutter.camera.CameraSurface;
import com.pheasant.shutterapp.shutter.presenter.CameraPresenter;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraPreviewFragment extends Fragment {

    private ImageButton swapButton;
    private ImageButton flashButton;
    private ImageButton takeButton;
    private ImageButton faceFocusButton;
    private ImageButton autoFocusButton;
    private Animation takeButtonAnimation;

    private CameraSurface cameraSurface;
    private CameraHolder cameraHolder;

    private CameraPresenter cameraPresenter;

    public CameraPreviewFragment() {
        this.cameraPresenter = new CameraPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_preview, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.cameraSurface = (CameraSurface) view.findViewById(R.id.shutter_surface);
        this.cameraHolder = new CameraHolder(this.cameraSurface);
        this.cameraHolder.setCameraListener(this.cameraPresenter);
        this.cameraPresenter.setCameraInterface(this.cameraHolder);

        this.swapButton = (ImageButton) view.findViewById(R.id.shutter_swap_button);
        this.flashButton = (ImageButton) view.findViewById(R.id.shutter_flash_button);
        this.takeButton = (ImageButton) view.findViewById(R.id.shutter_take_button);
        this.faceFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_face_button);
        this.autoFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_auto_button);
        this.takeButtonAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.rotate);
//        this.takeButton.startAnimation(this.takeButtonAnimation);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.cameraHolder != null)
            this.cameraHolder.onStart();
    }

    @Override
    public void onPause() {
        if (this.cameraHolder != null)
            this.cameraHolder.onStop();
        super.onPause();
    }
}
