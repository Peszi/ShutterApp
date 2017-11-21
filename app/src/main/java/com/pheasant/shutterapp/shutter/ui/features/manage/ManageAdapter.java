package com.pheasant.shutterapp.shutter.ui.features.manage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.PageController;
import com.pheasant.shutterapp.features.shutter.manage.search.InvitesFragment;
import com.pheasant.shutterapp.shared.views.LockingViewPager;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.utils.IntentKey;

/**
 * Created by Peszi on 2017-06-06.
 */

public class ManageAdapter extends FragmentPagerAdapter implements TabLayout.OnTabSelectedListener, PageController {

    private TabLayout tabLayout;
    private LockingViewPager viewPager;

    private FriendsTmpFragment friendsFragment;
    private InvitesFragment invitesFragment;

    public ManageAdapter(FragmentManager fragmentManager, View view, Bundle bundle) {
        super(fragmentManager);
        this.friendsFragment = new FriendsTmpFragment(bundle.getString(IntentKey.USER_API_KEY));
        this.friendsFragment.setArguments(bundle);
        this.invitesFragment = new InvitesFragment();
        this.invitesFragment.setArguments(bundle);
        this.setupView(view);
    }

    public void setupView(View view) {
        this.viewPager = (LockingViewPager) view.findViewById(R.id.profile_pager);
        this.viewPager.setOffscreenPageLimit(2);
        this.viewPager.setEnabled(false);
        this.viewPager.setAdapter(this);
        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);
    }

    public void setFriendsInterface(ShutterApiInterface friendsInterface) {
        this.friendsFragment.setFriendsInterface(friendsInterface);
    }

    public void onShow() {
        this.setPageTo(0);
    }

    @Override
    public void setPageTo(int position) {
        this.tabLayout.getTabAt(position).select();
    }

    /*
    * Manage navigation bar
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.setPage(tab.getPosition());
    }

    private void setPage(int position) {
//        this.getItem(position).onShow();
//        this.viewPager.setCurrentItem(position, true);
//        if (position == 1)
//            this.friendsFragment.setPageController(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return this.friendsFragment;
            case 1: return this.invitesFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
