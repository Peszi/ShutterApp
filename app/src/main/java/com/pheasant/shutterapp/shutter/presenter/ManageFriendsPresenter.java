package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendRemoveListener;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.api.requester.FriendRemoveRequester;
import com.pheasant.shutterapp.shutter.api.requester.InviteManageRequester;
import com.pheasant.shutterapp.shutter.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.listeners.ManageFriendsEventListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class ManageFriendsPresenter implements ManageFriendsEventListener, FriendsListListener, SearchListListener, InvitesListListener, InvitesListener, FriendRemoveListener {

    public final int FRIENDS_ADAPTER_IDX = 0;
    public final int INVITES_ADAPTER_IDX = 1;
    public final int STRANGERS_ADAPTER_IDX = 2;

    private ShutterApiInterface shutterApiInterface; // TODO on null object
    private InviteManageRequester inviteManageRequester;
    private FriendRemoveRequester friendRemoveRequester;

    private ManageFriendsView friendsView;

    public ManageFriendsPresenter(String apiKey) {
        this.inviteManageRequester = new InviteManageRequester(apiKey);
        this.inviteManageRequester.setListener(this);
        this.friendRemoveRequester = new FriendRemoveRequester(apiKey);
        this.friendRemoveRequester.setListener(this);
    }

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
        this.shutterApiInterface.downloadInvites();
        this.shutterApiInterface.downloadFriends();
    }

    @Override
    public void onFriendRemoveEvent(int userId) {
        this.friendRemoveRequester.deleteFriend(userId);
    }

    @Override
    public void onInviteEvent(int userId) {
        this.inviteManageRequester.sendInvite(userId);
    }

    @Override
    public void onInviteDeleteEvent(int userId) {
        this.inviteManageRequester.deleteInvite(userId);
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
    public void onFriendsListDownloaded(int changesCount) {
        this.friendsView.friendsListUpdate(this.shutterApiInterface.getFriends());
        this.showFriendsUpdateMessage(changesCount);
    }

    @Override
    public void onSearchListDownloaded(ArrayList<StrangerData> strangersList) {
        this.friendsView.strangersListUpdate(strangersList);
    }

    @Override
    public void onInvitesListDownloaded(int changesCount) {
        this.friendsView.invitesListUpdate(this.shutterApiInterface.getInvites());
        this.showInvitesUpdateMessage(changesCount);
    }

    private void showFriendsUpdateMessage(int size) {
        if (size > 0) { this.friendsView.showInfoMessage(size + " friends added"); }
        else if (size < 0) { this.friendsView.showInfoMessage(Math.abs(size) + " friends removed"); }
    }

    private void showInvitesUpdateMessage(int size) {
        if (size > 0) { this.friendsView.showInfoMessage(size + " invites added"); }
        else if (size < 0) { this.friendsView.showInfoMessage(Math.abs(size) + " invites removed"); }
    }

    @Override
    public void onFriendRemove(int friendId) {
        this.shutterApiInterface.downloadFriends();
    }

    @Override
    public void onInviteSent(int userId) {
        this.shutterApiInterface.reloadSearchResults();
    }

    @Override
    public void onInviteDeleted(int userId) {
        this.shutterApiInterface.reloadSearchResults();
    }

}
