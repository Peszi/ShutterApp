package com.pheasant.shutterapp.shutter.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.ShutterDataController;
import com.pheasant.shutterapp.shutter.tmp.FriendsTmpFragment;
import com.pheasant.shutterapp.shutter.ui.features.BrowseFragment;
import com.pheasant.shutterapp.shutter.ui.features.CameraFragment;
import com.pheasant.shutterapp.shutter.ui.features.ManageFragment;
import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;
import com.pheasant.shutterapp.utils.IntentKey;

/**
 * Created by Peszi on 2017-04-24.
 */

public class ShutterAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private final int FRAGMENTS_COUNT = 4;

    private NotifiableFragment[] shutterFragments;

    private LockingViewPager viewPager;
    private ShutterInterface shutterInterface;

    private ShutterDataController shutterDataController;

    public ShutterAdapter(FragmentManager fragmentManager, View view, Bundle bundle, ShutterInterface shutterInterface) {
        super(fragmentManager);
        this.shutterInterface = shutterInterface;
        this.shutterDataController = new ShutterDataController(bundle.getString(IntentKey.USER_API_KEY));
        this.setupViewPager(view);
        this.setupFragments(bundle);
    }

    public void setupViewPager(View view) {
        this.viewPager = (LockingViewPager) view.findViewById(R.id.shutter_view_pager);
        this.viewPager.setOffscreenPageLimit(this.FRAGMENTS_COUNT);
        this.viewPager.setEnabled(true);
        this.viewPager.setAdapter(this);
        this.viewPager.addOnPageChangeListener(this);
    }

    public void setupFragments(Bundle bundle) {
        this.shutterFragments = new NotifiableFragment[this.FRAGMENTS_COUNT];
        // Camera
        this.shutterFragments[0] = new CameraFragment();
        this.shutterFragments[0].setArguments(bundle);
        ((CameraFragment) this.shutterFragments[0]).setViewPager(this.viewPager);
        ((CameraFragment) this.shutterFragments[0]).setCameraActionListener((BrowseFragment) this.shutterFragments[1]);
        // Browse
        this.shutterFragments[1] = new BrowseFragment();
        this.shutterFragments[1].setArguments(bundle);
        // Manage
        this.shutterFragments[2] = new ManageFragment();
        this.shutterFragments[2].setArguments(bundle);
        // Friends
        this.shutterFragments[3] = new FriendsTmpFragment();
        this.shutterFragments[3].setArguments(bundle);
        ((FriendsTmpFragment) this.shutterFragments[3]).setFriendsInterface(this.shutterDataController);
    }

    @Override
    public Fragment getItem(int position) {
        return this.shutterFragments[position];
    }

    @Override
    public int getCount() {
        return this.FRAGMENTS_COUNT;
    }

    // Back button actions
    public void onBack() {
        if (!this.switchToPrevFragment())
            if (this.shutterFragments[this.viewPager.getCurrentItem()] instanceof CameraFragment) // Camera switch back (exit editing or logout)
                if (!((CameraFragment) this.shutterFragments[this.viewPager.getCurrentItem()]).isInEditor())
                    this.shutterInterface.logoutUser();
    }

    // Switching fragment to the first one
    private boolean switchToPrevFragment() {
        if (this.viewPager.getCurrentItem() > 0) {
            this.viewPager.setCurrentItem(this.viewPager.getCurrentItem() - 1, true);
            return true;
        }
        return false;
    }

    // On visible fragment change
    @Override
    public void onPageSelected(int position) {
        this.shutterFragments[position].onShow();
    }

    // Not used
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    // Not used
    @Override
    public void onPageScrollStateChanged(int state) {

    }

}