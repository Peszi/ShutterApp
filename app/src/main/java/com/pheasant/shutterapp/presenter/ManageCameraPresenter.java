package com.pheasant.shutterapp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.pheasant.shutterapp.ui.interfaces.CameraEditorInterface;
import com.pheasant.shutterapp.ui.shared.LockingViewPager;
import com.pheasant.shutterapp.api.ShutterApiInterface;
import com.pheasant.shutterapp.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.api.listeners.PhotoUploadListener;
import com.pheasant.shutterapp.ui.dialog.RecipientsListDialog;
import com.pheasant.shutterapp.ui.interfaces.CameraManageView;
import com.pheasant.shutterapp.ui.listeners.EditorListener;
import com.pheasant.shutterapp.ui.listeners.CameraListener;
import com.pheasant.shutterapp.ui.listeners.CameraManageViewListener;

import java.util.List;

/**
 * Created by Peszi on 2017-11-24.
 */

public class ManageCameraPresenter implements CameraManageViewListener, CameraListener, EditorListener, PhotoUploadListener, FriendsListListener, RecipientsListDialog.RecipientsDialogListener {

    private RecipientsListDialog recipientsListDialog;
    private CameraEditorInterface editorInterface;
    private LockingViewPager pagerInterface;
    private CameraManageView cameraManageView;

    private ShutterApiInterface shutterApiInterface;

    private boolean canLogout;

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

    public void setEditorInterface(CameraEditorInterface editorInterface) {
        this.editorInterface = editorInterface;
    }

    // View Callbacks

    @Override
    public void setupView(Context context) {
        this.recipientsListDialog = new RecipientsListDialog(context);
        this.recipientsListDialog.setRecipientsDialogListener(this);
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
            this.recipientsListDialog.updateRecipients(this.shutterApiInterface.getFriends());
            this.recipientsListDialog.showDialog();
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
            this.recipientsListDialog.updateRecipients(this.shutterApiInterface.getFriends());
    }

    // Uploader Callback

    @Override
    public void onPhotoUploadStatusChange(boolean success) {
        if (success) {
            this.cameraManageView.showToastMessage("photo successfully uploaded");
            if (this.shutterApiInterface != null)
                this.shutterApiInterface.downloadPhotos();
        } else {
            if (this.shutterApiInterface != null)
                this.shutterApiInterface.reUploadPhotos();
        }
    }

    // Other

    private void setCameraMode() {
        this.editorInterface.onClose();
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
