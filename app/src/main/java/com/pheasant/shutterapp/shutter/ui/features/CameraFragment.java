package com.pheasant.shutterapp.shutter.ui.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.shared.LockingViewPager;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.presenter.ManageCameraPresenter;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraEditorFragment;
import com.pheasant.shutterapp.shutter.ui.features.camera.CameraHolderFragment;
import com.pheasant.shutterapp.shutter.ui.interfaces.CameraManageView;
import com.pheasant.shutterapp.shutter.ui.shared.NotifiableFragment;

/**
 * Created by Peszi on 2017-04-24.
 */

public class CameraFragment extends NotifiableFragment implements CameraManageView {

    private CameraHolderFragment previewFragment;
    private CameraEditorFragment editorFragment;

    private ManageCameraPresenter cameraPresenter;

    private FragmentManager fragmentManager;

    public CameraFragment() {
        this.cameraPresenter = new ManageCameraPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_camera_fragment, container, false);

        // UI
        this.cameraPresenter.setupView(this.getContext());
        this.cameraPresenter.setManageCameraView(this);

        // Fragments
        this.fragmentManager = this.getFragmentManager();
        this.previewFragment = new CameraHolderFragment();
        this.previewFragment.setCameraListener(this.cameraPresenter);
        this.editorFragment = new CameraEditorFragment();
        this.editorFragment.setEditorListener(this.cameraPresenter);

        this.cameraPresenter.setEditorInterface(this.editorFragment);
        this.cameraPresenter.onPageShow();

        return view;
    }

    public void setShutterDataManager(ShutterApiInterface friendsInterface) {
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
    public void setEditorMode() {
        this.setFragment(this.editorFragment);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this.getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
}