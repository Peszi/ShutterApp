package com.pheasant.shutterapp.shutter.ui.features;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraEditorFragment;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraPreviewFragment;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraManageView;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;

import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

public class CameraFragment extends NotifiableFragment implements View.OnClickListener, CameraManageView {

//    private CameraManager cameraManager;
//    private EditorManager editorManager;
//    private LockingViewPager viewPager;
//
//    private CameraActionListener cameraActionListener;

    private CameraPreviewFragment previewFragment;
    private CameraEditorFragment editorFragment;

    private ManageCameraPresenter cameraPresenter;

    private FragmentManager fragmentManager;

    public CameraFragment() {
        this.cameraPresenter = new ManageCameraPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_fragment, container, false);
//        this.editorManager = new EditorManager(this.getContext(), view, this);
//        this.cameraManager = new CameraManager(this.getContext(), view, this.editorManager);
//        this.cameraManager.setPhotoTakeListener(this);

        this.previewFragment = new CameraPreviewFragment();
        this.previewFragment.setCameraListener(this.cameraPresenter);
        this.editorFragment = new CameraEditorFragment();
        this.editorFragment.setEditorListener(this.cameraPresenter);

        this.fragmentManager = this.getFragmentManager();
        this.cameraPresenter.setManageCameraView(this);

        this.cameraPresenter.onPageShow();

        return view;
    }

    public void setFriendsInterface(ShutterApiInterface friendsInterface) {
        this.cameraPresenter.setShutterApiInterface(friendsInterface);
    }

    public void setPagerInterface(LockingViewPager pagerInterface) {
        this.cameraPresenter.setPagerInterface(pagerInterface);
    }

    // UI Events handling

    @Override
    public void onShow() {
        this.cameraPresenter.onPageShow();
    }

    @Override
    public boolean onBack() {
        return this.cameraPresenter.onBackBtn();
    }

    @Override
    public void setCameraMode() {
        this.setFragment(this.previewFragment);
    }

    @Override
    public void setEditorMode(Bitmap cameraPhoto) {
        this.editorFragment.setPhoto(cameraPhoto);
        this.setFragment(this.editorFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.previewFragment.onResume();
    }

    @Override
    public void onPause() {
        this.previewFragment.onPause();
        super.onPause();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_camera_base, fragment);
        transaction.commit();
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
}