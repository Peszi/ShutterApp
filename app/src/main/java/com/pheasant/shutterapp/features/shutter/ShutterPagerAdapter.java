package com.pheasant.shutterapp.features.shutter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.browse.PhotosFragment;
import com.pheasant.shutterapp.features.shutter.camera.CameraFragment;
import com.pheasant.shutterapp.features.shutter.browse.user.UserFragment;
import com.pheasant.shutterapp.shared.views.LockingViewPager;

/**
 * Created by Peszi on 2017-04-24.
 */

public class ShutterPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private static final int CAMERA_STATE = 0;
    private static final int BROWSE_STATE = 1;
    private static final int FRIENDS_STATE = 2;

    private LockingViewPager viewPager;
    private CameraFragment cameraFragment;
    private PhotosFragment photosFragment;
    private UserFragment userFragment;

    public ShutterPagerAdapter(FragmentManager fragmentManager, View view, Bundle bundle) {
        super(fragmentManager);
        this.setupView(view);
        this.cameraFragment = new CameraFragment();
        this.cameraFragment.setArguments(bundle);
        this.cameraFragment.setViewPager(viewPager);
        this.cameraFragment.setCameraActionListener(this.photosFragment);
        this.photosFragment = new PhotosFragment();
        this.photosFragment.setArguments(bundle);
        this.userFragment = new UserFragment();
        this.userFragment.setArguments(bundle);
    }

    public void setupView(View view) {
        this.viewPager = (LockingViewPager) view.findViewById(R.id.shutter_view_pager);
        this.viewPager.setOffscreenPageLimit(3);
        this.viewPager.setEnabled(true);
        this.viewPager.setAdapter(this);
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override
    public ShutterFragment getItem(int position) {
        switch (position) {
            case CAMERA_STATE: return this.cameraFragment;
            case BROWSE_STATE: return this.photosFragment;
            case FRIENDS_STATE: return this.userFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void onPageSelected(int position) {
        this.getItem(position).onShow();
    }

    public void onBack() {
        if (this.viewPager.getCurrentItem() > 0)
            this.viewPager.setCurrentItem(this.viewPager.getCurrentItem()-1, true);
        else
            this.cameraFragment.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}