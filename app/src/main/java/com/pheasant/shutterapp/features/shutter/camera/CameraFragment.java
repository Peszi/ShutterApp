package com.pheasant.shutterapp.features.shutter.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.editor.EditorManager;
import com.pheasant.shutterapp.features.shutter.camera.recipients.RecipientsDialog;
import com.pheasant.shutterapp.features.shutter.ShutterFragment;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.photos.PhotoUploadRequest;
import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;
import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.utils.IntentKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

public class CameraFragment extends ShutterFragment implements CameraManager.PhotoTakeListener, View.OnClickListener, RecipientsDialog.ActionListener {

    private CameraManager cameraManager;
    private EditorManager editorManager;
    private LockingViewPager viewPager;

    private CameraActionListener cameraActionListener;

    private RecipientsDialog recipientsDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_fragment, container, false);
        this.editorManager = new EditorManager(this.getContext(), view, this);
        this.cameraManager = new CameraManager(this.getContext(), view, this.editorManager);
        this.cameraManager.setPhotoTakeListener(this);
        this.recipientsDialog = new RecipientsDialog(this.getContext());
        this.recipientsDialog.setActionListener(this);
        return view;
    }

    public void setViewPager(LockingViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setCameraActionListener(CameraActionListener cameraActionListener) {
        this.cameraActionListener = cameraActionListener;
    }

    private void startEditing(Bitmap bitmap) {
        this.viewPager.setEnabled(false);
        this.editorManager.startEditing(bitmap);
    }

    private void stopEditing() {
        this.viewPager.setEnabled(true);
        this.editorManager.stopEditing();
    }

    public void logoutAccount() {
        this.getActivity().finish();
    }

    @Override
    public void onPhotoTaken(Bitmap bitmap) {
        this.startEditing(bitmap);
    }

    public void onBackPressed() {
        if (this.editorManager.isEditing()) {
            this.stopEditing();
        } else {
            this.logoutAccount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.cameraManager != null)
            this.cameraManager.onResume();
    }

    @Override
    public void onPause() {
        if (this.cameraManager != null)
            this.cameraManager.onPause();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.preview_accept: this.recipientsDialog.showDialog(this.cameraActionListener.getFriendsData()); break;
//            case R.id.preview_reject: this.stopEditing(); break;
//        }
        List<Integer> recipients = new ArrayList<>();
        recipients.add(1);
        PhotoUploadRequest uploadRequest = new PhotoUploadRequest(this.editorManager.getEditedPhoto(), recipients, this.getArguments().getString(IntentKey.USER_API_KEY));
        uploadRequest.setOnRequestResultListener(new OnRequestResultListener() {
            @Override
            public void onResult(int resultCode) {

            }
        });
        uploadRequest.execute();
    }

    @Override
    public void onAccept(List<Integer> recipients) {
        this.stopEditing();
        this.cameraActionListener.onPhotoDone(this.editorManager.getEditedPhoto(), recipients);
    }

    @Override
    public void onShow() {

    }

    public interface CameraActionListener {
        void onPhotoDone(Bitmap bitmap, List<Integer> recipients);
        List<UserData> getFriendsData();
    }
}