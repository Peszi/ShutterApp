package com.pheasant.shutterapp.shutter.ui.features;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.camera.recipients.RecipientsDialog;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.presenter.ManageCameraPresenter;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraPreviewFragment;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;

import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

public class CameraFragment extends NotifiableFragment implements View.OnClickListener, RecipientsDialog.ActionListener {

//    private CameraManager cameraManager;
//    private EditorManager editorManager;
//    private LockingViewPager viewPager;
//
//    private CameraActionListener cameraActionListener;

    private CameraPreviewFragment previewFragment;

    private ManageCameraPresenter cameraPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_fragment, container, false);
//        this.editorManager = new EditorManager(this.getContext(), view, this);
//        this.cameraManager = new CameraManager(this.getContext(), view, this.editorManager);
//        this.cameraManager.setPhotoTakeListener(this);

        this.previewFragment = new CameraPreviewFragment();

        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_camera_base, this.previewFragment);
        transaction.commit();

        return view;
    }

    public void setFriendsInterface(ShutterApiInterface friendsInterface) {
        this.cameraPresenter.setShutterApiInterface(friendsInterface);
    }

    // UI Events handling

    @Override
    public void onShow() {

    }


    public void setViewPager(LockingViewPager viewPager) {
//        this.viewPager = viewPager;
    }

    public void setCameraActionListener(CameraActionListener cameraActionListener) {
//        this.cameraActionListener = cameraActionListener;
    }

    private void startEditing(Bitmap bitmap) {
//        this.viewPager.setEnabled(false);
//        this.editorManager.startEditing(bitmap);
    }

    private void stopEditing() {
//        this.viewPager.setEnabled(true);
//        this.editorManager.stopEditing();
    }

    public boolean isInEditor() {
//        if (this.editorManager.isEditing()) {
//            this.stopEditing();
//            return true;
//        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.previewFragment.onResume();
//
//        if (this.cameraManager != null)
//            this.cameraManager.onResume();
    }

    @Override
    public void onPause() {
//        if (this.cameraManager != null)
//            this.cameraManager.onPause();
        this.previewFragment.onPause();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.preview_accept: this.recipientsDialog.showDialog(this.cameraActionListener.getFriendsData()); break;
//            case R.id.preview_reject: this.stopEditing(); break;
//        }
//        List<Integer> recipients = new ArrayList<>();
//        recipients.add(1);
//        PhotoUploadRequest uploadRequest = new PhotoUploadRequest(this.editorManager.getEditedPhoto(), recipients, this.getArguments().getString(IntentKey.USER_API_KEY));
//        uploadRequest.setOnRequestResultListener(new RequestResultListener() {
//            @Override
//            public void onResult(int resultCode) {
//
//            }
//        });
//        uploadRequest.execute();
    }

    @Override
    public void onAccept(List<Integer> recipients) {
        this.stopEditing();
//        this.cameraActionListener.onPhotoDone(this.editorManager.getEditedPhoto(), recipients);
    }

    public interface CameraActionListener {
        void onPhotoDone(Bitmap bitmap, List<Integer> recipients);
        List<UserData> getFriendsData();
    }
}