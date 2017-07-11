package com.pheasant.shutterapp.features.shutter.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.camera.utils.CameraController;
import com.pheasant.shutterapp.features.shutter.camera.utils.CameraSurface;
import com.pheasant.shutterapp.features.shutter.editor.EditorManager;

/**
 * Created by Peszi on 2017-05-03.
 */

public class CameraManager implements View.OnClickListener {

    private Vibrator shutterVibrator;
    private CameraSurface cameraSurface;
    private CameraController cameraController;

    private Context mainContext;
    private View mainView;
    private ImageButton swapButton;
    private ImageButton flashButton;
    private ImageButton takeButton;
    private ImageButton faceFocusButton;
    private ImageButton autoFocusButton;
    private Animation takeButtonAnimation;

    private PhotoTakeListener photoTakeListener;

    // TODO stop preview

    public CameraManager(Context context, View view, EditorManager editorManager) {
        this.shutterVibrator = ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE));
        this.mainContext = context;
        this.mainView = view;
        this.setupCamera(this.mainView);
        this.setupCameraButtons(context, this.mainView);
    }

    public void setPhotoTakeListener(PhotoTakeListener photoTakeListener) {
        this.photoTakeListener = photoTakeListener;
    }

    private void setupCamera(View view) {
        this.cameraSurface = (CameraSurface) view.findViewById(R.id.shutter_surface);
        this.cameraController = new CameraController(this, this.cameraSurface);
    }

    private void setupCameraButtons(Context context, View view) {
        this.swapButton = (ImageButton) view.findViewById(R.id.shutter_swap_button);
        this.flashButton = (ImageButton) view.findViewById(R.id.shutter_flash_button);
        this.takeButton = (ImageButton) view.findViewById(R.id.shutter_take_button);
        this.faceFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_face_button);
        this.autoFocusButton = (ImageButton) view.findViewById(R.id.shutter_focus_auto_button);
        // Listeners
        this.swapButton.setOnClickListener(this);
        this.flashButton.setOnClickListener(this);
        this.takeButton.setOnClickListener(this);
        this.faceFocusButton.setOnClickListener(this);
        this.autoFocusButton.setOnClickListener(this);
        // Take animation
        this.takeButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);
    }

    public void onResume() {
        this.cameraController.onStart();
    }

    public void onPause() {
        this.cameraController.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shutter_swap_button:
                this.cameraController.swapCamera(this.mainContext); break;
            case R.id.shutter_flash_button:
                this.cameraController.changeFlashMode(this.mainContext); break;
            case R.id.shutter_take_button:
                this.cameraController.takePhoto();
                this.takeButton.startAnimation(this.takeButtonAnimation); break;
            case R.id.shutter_focus_face_button:
                this.cameraController.switchToFaceFocus(); break;
            case R.id.shutter_focus_auto_button:
                this.cameraController.switchToAutoFocus(); break;
        }
        this.reloadButtonsImages();
    }

    private void reloadButtonsImages() {
        // swap button
        int buttonImage = R.drawable.shutter_face_button;
        if (this.cameraController.isBackgroundCamera())
            buttonImage = R.drawable.shutter_landscape_button;
        this.swapButton.setImageResource(buttonImage);
        // flash button
        buttonImage = R.drawable.shutter_flash_off_button;
        switch (this.cameraController.getCameraFlashMode()) {
            case 0: buttonImage = R.drawable.shutter_flash_off_button; break;
            case 1: buttonImage = R.drawable.shutter_flash_on_button; break;
            case 2: buttonImage = R.drawable.shutter_flash_auto_button; break;
        }
        this.flashButton.setImageResource(buttonImage);
    }

    public void hideFaceFocusButton() {
        this.faceFocusButton.setVisibility(View.GONE);
    }

    public void hideAutoFocusButton() {
        this.autoFocusButton.setVisibility(View.GONE);
    }

    public void showFocusButtons() {
        this.faceFocusButton.setVisibility(View.VISIBLE);
        this.autoFocusButton.setVisibility(View.VISIBLE);
    }

    public void setPhoto(Bitmap bitmap) {
        if (this.photoTakeListener != null)
            this.photoTakeListener.onPhotoTaken(bitmap);
    }

    public void makeVibe(long time) {
        if (this.shutterVibrator != null)
            this.shutterVibrator.vibrate(time);
    }

    public interface PhotoTakeListener {
        void onPhotoTaken(Bitmap bitmap);
    }
}
