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
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraPreviewView;
import com.pheasant.shutterapp.shutter.presenter.CameraHolderPresenter;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-24.
 */

public class CameraPreviewFragment extends Fragment implements CameraPreviewView, View.OnClickListener {

    private ImageButton swapButton;
    private ImageButton flashButton;
    private ImageButton takeButton;
    private ImageButton faceFocusButton;
    private ImageButton autoFocusButton;
    private Animation takeButtonAnimation;

    private CameraSurface cameraSurface;
    private CameraHolder cameraHolder;

    private CameraHolderPresenter cameraPresenter;

    public CameraPreviewFragment() {
        this.cameraPresenter = new CameraHolderPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_preview, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.setupUI(view);

        this.cameraHolder = new CameraHolder(this.cameraSurface.getHolder());
        this.cameraSurface.setSurfaceListener(this.cameraPresenter);
        this.cameraHolder.setCameraListener(this.cameraPresenter);
        this.cameraPresenter.setCameraInterface(this.cameraHolder);
        this.cameraPresenter.setCameraSurfaceInterface(this.cameraSurface);
        this.cameraPresenter.setCameraViewInterface(this);

        return view;
    }

    private void setupUI(View view) {
        this.cameraSurface = (CameraSurface) view.findViewById(R.id.shutter_surface);
        this.swapButton = (ImageButton) view.findViewById(R.id.shutter_swap_button);
        this.flashButton = (ImageButton) view.findViewById(R.id.shutter_flash_button);
        this.takeButton = (ImageButton) view.findViewById(R.id.shutter_take_button);
        this.faceFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_face_button);
        this.autoFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_auto_button);
        this.takeButtonAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.rotate);
        this.swapButton.setOnClickListener(this);
        this.flashButton.setOnClickListener(this);
        this.takeButton.setOnClickListener(this);
        this.faceFocusButton.setOnClickListener(this);
        this.autoFocusButton.setOnClickListener(this);
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

    @Override
    public void startTakePhotoAnimation() {
        this.takeButton.startAnimation(this.takeButtonAnimation);
    }

    @Override
    public void changeCameraSwapIcon(int iconIdx) {
        int resId = 0;
        switch (iconIdx) {
            case 0: resId = R.drawable.shutter_landscape_button; break;
            case 1: resId = R.drawable.shutter_face_button; break;
        }
        this.swapButton.setImageResource(resId);
    }

    @Override
    public void changeFlashModeIcon(int iconIdx) {
        int resId = 0;
        switch (iconIdx) {
            case 0: resId = R.drawable.shutter_flash_off_button; break;
            case 1: resId = R.drawable.shutter_flash_on_button; break;
            case 2: resId = R.drawable.shutter_flash_auto_button; break;
        }
        this.flashButton.setImageResource(resId);
    }

    @Override
    public void showFaceFocusIcon(boolean show) {
        this.showView(this.faceFocusButton, show);
    }

    @Override
    public void showAutoFocusIcon(boolean show) {
        this.showView(this.autoFocusButton, show);
    }

    private void showView(View view, boolean show) {
        if (show) { view.setVisibility(View.VISIBLE); }
        else { view.setVisibility(View.GONE); }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shutter_swap_button: this.cameraPresenter.onSwapCameraEvent(); break;
            case R.id.shutter_flash_button: this.cameraPresenter.onChangeFlashModeEvent(); break;
            case R.id.shutter_take_button: this.cameraPresenter.onTakePhotoEvent(); break;
            case R.id.shutter_focus_face_button: this.cameraPresenter.onFaceFocusEvent(); break;
            case R.id.shutter_focus_auto_button: this.cameraPresenter.onAutoFocusEvent(); break;
        }
    }
}
