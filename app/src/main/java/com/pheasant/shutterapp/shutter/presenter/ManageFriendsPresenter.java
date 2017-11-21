package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.listeners.ManageFriendsEventListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class ManageFriendsPresenter implements ManageFriendsEventListener, FriendsListListener, SearchListListener {

    private ShutterApiInterface shutterApiInterface;

    private ManageFriendsView friendsView;

    public ManageFriendsPresenter(String apiKey) {

    }

    public void setView(ManageFriendsView friendsView) {
        this.friendsView = friendsView;
    }

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
        this.shutterApiInterface.registerFriendsListListener(this);
        this.shutterApiInterface.setSearchListListener(this);
    }

    @Override
    public void onPageShow() {
        this.friendsView.searchClearKeyword();
        this.friendsView.tabForceSelect(0);
        this.setupFriendsList();
    }

    @Override
    public void onTabSelected(int index) {
        switch (index) {
            case 0: this.setupFriendsList(); break;
            case 1: this.setupStrangersList(); break;
            case 2: this.setupFriendsList(); break;
        }
    }

    @Override
    public void onKeywordChange(String keyword) {
        this.friendsView.friendsSetKeywordFilter(keyword);
        if (keyword.isEmpty()) { this.friendsView.strangersListClear(); }
        else { this.shutterApiInterface.searchUsers(keyword); }
    }

    @Override
    public void onRefreshButton() {
        this.shutterApiInterface.downloadFriends();
    }

    private void setupFriendsList() {
        this.friendsView.refreshShowButton(true);
        this.friendsView.searchClearKeyword();
        this.friendsView.searchSetIcon(0);
        this.friendsView.listSetAdapter(0);
        this.shutterApiInterface.downloadFriends();
    }

    private void setupStrangersList() {
        this.friendsView.refreshShowButton(false);
        this.friendsView.searchSetIcon(1);
        this.friendsView.listSetAdapter(1);
    }

    @Override
    public void onFriendsListDownloaded(ArrayList<FriendData> friendsList) {
        this.friendsView.friendsListUpdate(friendsList);
    }

    @Override
    public void onSearchListDownloaded(ArrayList<StrangerData> strangersList) {
        this.friendsView.strangersListUpdate(strangersList);
    }
}
