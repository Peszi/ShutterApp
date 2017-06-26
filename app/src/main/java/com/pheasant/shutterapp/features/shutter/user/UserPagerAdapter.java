package com.pheasant.shutterapp.features.shutter.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.ShutterFragment;
import com.pheasant.shutterapp.features.shutter.user.friends.FriendsFragment;
import com.pheasant.shutterapp.features.shutter.user.friends.PageController;
import com.pheasant.shutterapp.features.shutter.user.profile.ProfileFragment;
import com.pheasant.shutterapp.features.shutter.user.search.InvitesFragment;
import com.pheasant.shutterapp.shared.views.LockingViewPager;

/**
 * Created by Peszi on 2017-06-06.
 */

public class UserPagerAdapter extends FragmentPagerAdapter implements TabLayout.OnTabSelectedListener, PageController {

    private TabLayout tabLayout;
    private LockingViewPager viewPager;
    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private InvitesFragment invitesFragment;

    public UserPagerAdapter(FragmentManager fragmentManager, View view, Bundle bundle) {
        super(fragmentManager);
        this.profileFragment = new ProfileFragment();
        this.profileFragment.setArguments(bundle);
        this.friendsFragment = new FriendsFragment();
        this.friendsFragment.setArguments(bundle);
        this.invitesFragment = new InvitesFragment();
        this.invitesFragment.setArguments(bundle);
        this.setupView(view);
    }

    public void setupView(View view) {
        this.viewPager = (LockingViewPager) view.findViewById(R.id.profile_pager);
        this.viewPager.setOffscreenPageLimit(3);
        this.viewPager.setEnabled(false);
        this.viewPager.setAdapter(this);
        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);
    }

    public void onShow() {
        this.setPageTo(1);
    }

    @Override
    public void setPageTo(int position) {
        this.tabLayout.getTabAt(position).select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.setPage(tab.getPosition());
    }

    private void setPage(int position) {
        this.getItem(position).onShow();
        this.viewPager.setCurrentItem(position, true);
        if (position == 1)
            this.friendsFragment.setPageController(this);
    }

    @Override
    public ShutterFragment getItem(int position) {
        switch (position) {
            case 0: return this.profileFragment;
            case 1: return this.friendsFragment;
            case 2: return this.invitesFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
