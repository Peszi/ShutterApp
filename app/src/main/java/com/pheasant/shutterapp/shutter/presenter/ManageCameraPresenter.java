package com.pheasant.shutterapp.shutter.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.shutter.ui.dialog.RecipientsDialog;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraEditorFragment;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraManageView;
import com.pheasant.shutterapp.shutter.ui.listeners.EditorListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraListener;
import com.pheasant.shutterapp.shutter.ui.listeners.CameraManageViewListener;

import java.util.List;

/**
 * Created by Peszi on 2017-11-24.
 */

public class ManageCameraPresenter implements CameraManageViewListener, CameraListener, EditorListener, PhotoUploadListener, FriendsListListener, RecipientsDialog.RecipientsDialogListener {

    private RecipientsDialog recipientsDialog;

    private ShutterApiInterface shutterApiInterface;
    private CameraEditorFragment editorInterface;
    private LockingViewPager pagerInterface;

    private CameraManageView cameraManageView;

    private boolean canLogout;

    public ManageCameraPresenter() {}

    // Setters

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
        this.shutterApiInterface.setPhotoUploadListener(this);
        this.shutterApiInterface.registerFriendsListListener(this);
    }

    public void setPagerInterface(LockingViewPager pagerInterface) {
        this.pagerInterface = pagerInterface;
    }

    public void setManageCameraView(CameraManageView cameraManageView) {
        this.cameraManageView = cameraManageView;
    }

    public void setEditorInterface(CameraEditorFragment editorInterface) {
        this.editorInterface = editorInterface;
    }

    // View Callbacks

    @Override
    public void setupView(Context context) {
        this.recipientsDialog = new RecipientsDialog(context);
        this.recipientsDialog.setRecipientsDialogListener(this);
    }

    @Override
    public void onPageShow() {
        this.setCameraMode();
    }

    @Override
    public boolean onBackBtn() {
        if (!this.canLogout) {
            this.setCameraMode();
            return false;
        }
        return this.canLogout;
    }

    // Camera Callbacks

    @Override
    public void onPhotoEvent(Bitmap cameraPhoto) {
        if (this.editorInterface != null)
            this.editorInterface.setNewPhoto(cameraPhoto);
        this.setEditorMode();
    }

    @Override
    public void onCameraErrorMessageEvent(String message) {
        this.cameraManageView.showToastMessage(message);
    }

    // Editor Callbacks

    @Override
    public void onPhotoAccepted() {
        if (this.shutterApiInterface != null) {
            this.recipientsDialog.updateRecipients(this.shutterApiInterface.getFriends());
            this.recipientsDialog.showDialog();
        }
    }

    @Override
    public void onPhotoRejected() {
        this.setCameraMode();
    }

    // Dialog

    @Override
    public void onRecipientsPicked(List<Integer> recipients) {
        if (this.shutterApiInterface != null && this.editorInterface != null)
            this.shutterApiInterface.uploadPhoto(this.editorInterface.getEditedPhoto(), recipients);
        this.setCameraMode();
    }

    @Override
    public void onRecipientsRefresh() {
        if (this.shutterApiInterface != null)
            this.shutterApiInterface.downloadFriends();
    }

    // Friends List Callback

    @Override
    public void onFriendsListDownloaded(int changesCount) {
        if (this.shutterApiInterface != null)
            this.recipientsDialog.updateRecipients(this.shutterApiInterface.getFriends());
    }

    // Uploader Callback

    @Override
    public void onPhotoUploadStatusChange(boolean success) {
        if (success) { this.cameraManageView.showToastMessage("photo successfully uploaded"); } else {
            if (this.shutterApiInterface != null)
                this.shutterApiInterface.reUploadPhotos();
        }
    }

    // Other

    private void setCameraMode() {
        this.pagerInterface.setEnabled(true);
        this.cameraManageView.setCameraMode();
        this.canLogout = true;
    }

    private void setEditorMode() {
        this.pagerInterface.setEnabled(false);
        this.cameraManageView.setEditorMode();
        this.canLogout = false;
    }
}
