package com.pheasant.shutterapp.shutter.tmp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.friends.SearchBar;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsDataInterface;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsListListener;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-11-07.
 */

public class FriendsTmpFragment extends NotifiableFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private SearchBar searchBar;
    private TabLayout tabLayout;
    private FloatingActionButton refreshButton; // TODO tmp reload button

    private UsersListManager listManager;

    private FriendsDataInterface friendsDataInterface;

    public FriendsTmpFragment() {
        this.listManager = new UsersListManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_friends_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.searchBar = new SearchBar(this.getContext(), view.findViewById(R.id.search_bar));

        this.tabLayout = (TabLayout) view.findViewById(R.id.profile_bottom_nav);
        this.tabLayout.setOnTabSelectedListener(this);

        this.refreshButton = (FloatingActionButton) view.findViewById(R.id.friends_refresh);
        this.refreshButton.setOnClickListener(this);

        this.listManager.setup(view, this.getContext(), this.getArguments());
        this.searchBar.setSearchListener(this.listManager);

        return view;
    }

    public void setFriendsInterface(FriendsDataInterface friendsInterface) {
        this.friendsDataInterface = friendsInterface;
    }

    // Tab change handling (list of friends<>strangers)
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch(tab.getPosition()) {
            case 0: this.setupFriendsList(); break;
            case 1: this.setupStrangersList(); break;
        }
    }

    private void setupFriendsList() {
        this.searchBar.clearKeyword();
        this.refreshButton.show();
        this.searchBar.setIconToFriends();
        this.listManager.attachFriends();
        this.downloadFriends();
        Log.d("RESPONSE", "[friends list attached]");
    }

    private void setupStrangersList() {
        this.refreshButton.hide();
        this.searchBar.setIconToSearch();
        this.listManager.attachStrangers();
    }

    // Updating friends list data
    @Override
    public void onShow() {
        this.searchBar.clearKeyword();
        this.tabLayout.getTabAt(0).select(); // Set tab to friends
        this.setupFriendsList();
        Log.d("RESPONSE", "[friends list attached2]");
//        this.downloadFriends();
    }

    @Override
    public void onClick(View v) {
        this.downloadFriends();
    }

    private void downloadFriends() {
        this.friendsDataInterface.updateFriends();
    }

    // Not used
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }
    // Not used
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public FriendsListListener getFriendsListener() {
        return this.listManager;
    }

}
