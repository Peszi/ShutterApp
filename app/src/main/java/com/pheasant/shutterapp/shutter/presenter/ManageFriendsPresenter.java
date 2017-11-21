package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.listeners.ManageFriendsEventListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class ManageFriendsPresenter implements ManageFriendsEventListener, FriendsListListener, SearchListListener, InvitesListListener {

    public final int FRIENDS_ADAPTER_IDX = 0;
    public final int INVITES_ADAPTER_IDX = 1;
    public final int STRANGERS_ADAPTER_IDX = 2;

    private ShutterApiInterface shutterApiInterface; // TODO on null object

    private ManageFriendsView friendsView;

    public ManageFriendsPresenter(String apiKey) {}

    public void setView(ManageFriendsView friendsView) {
        this.friendsView = friendsView;
    }

    public void setShutterApiInterface(ShutterApiInterface shutterApiInterface) {
        this.shutterApiInterface = shutterApiInterface;
        this.shutterApiInterface.registerFriendsListListener(this);
        this.shutterApiInterface.setSearchListListener(this);
        this.shutterApiInterface.setInvitesListListener(this);
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
            case FRIENDS_ADAPTER_IDX: this.setupFriendsList(); break;
            case INVITES_ADAPTER_IDX: this.setupInvitesList(); break;
            case STRANGERS_ADAPTER_IDX: this.setupStrangersList(); break;
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
        this.shutterApiInterface.downloadInvites();
    }

    private void setupFriendsList() {
        this.friendsView.refreshShowButton(true);
        this.friendsView.searchClearKeyword();
        this.friendsView.searchSetIcon(this.FRIENDS_ADAPTER_IDX);
        this.friendsView.listSetAdapter(this.FRIENDS_ADAPTER_IDX);
        this.shutterApiInterface.downloadFriends();
    }

    private void setupInvitesList() {
        this.friendsView.refreshShowButton(true);
        this.friendsView.searchSetIcon(this.INVITES_ADAPTER_IDX); // TODO just icon
        this.friendsView.listSetAdapter(this.INVITES_ADAPTER_IDX);
        this.shutterApiInterface.downloadInvites();
    }

    private void setupStrangersList() {
        this.friendsView.refreshShowButton(false);
        this.friendsView.searchSetIcon(this.STRANGERS_ADAPTER_IDX);
        this.friendsView.listSetAdapter(this.STRANGERS_ADAPTER_IDX);
    }

    @Override
    public void onFriendsListDownloaded(ArrayList<FriendData> friendsList) {
        this.friendsView.friendsListUpdate(this.shutterApiInterface.getFriends());
        this.showFriendsUpdateMessage(friendsList.size());
    }

    @Override
    public void onSearchListDownloaded(ArrayList<StrangerData> strangersList) {
        this.friendsView.strangersListUpdate(strangersList);
    }

    @Override
    public void onInvitesListDownloaded(ArrayList<UserData> invitesList) {
        this.friendsView.invitesListUpdate(this.shutterApiInterface.getInvites());
        this.showInvitesUpdateMessage(invitesList.size());
    }

    private void showFriendsUpdateMessage(int size) {
        if (size > 0) { this.friendsView.showInfoMessage(size + " friends added"); }
    }

    private void showInvitesUpdateMessage(int size) {
        if (size > 0) { this.friendsView.showInfoMessage(size + " invites added"); }
    }
}
