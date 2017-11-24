package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendRemoveListener;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesAcceptListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesCreateListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.api.requester.FriendRemoveRequester;
import com.pheasant.shutterapp.shutter.api.requester.InviteAcceptRequester;
import com.pheasant.shutterapp.shutter.api.requester.InviteCreateRequester;
import com.pheasant.shutterapp.shutter.interfaces.ManageFriendsView;
import com.pheasant.shutterapp.shutter.listeners.ManageFriendsEventListener;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.FriendObject;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.InviteObject;
import com.pheasant.shutterapp.shutter.ui.features.manage.object.StrangerObject;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class ManageFriendsPresenter implements ManageFriendsEventListener, FriendsListListener, SearchListListener, InvitesListListener, FriendRemoveListener, InvitesAcceptListener, InvitesCreateListener, FriendObject.FriendRemoveBtnListener, InviteObject.InviteAcceptBtnListener, StrangerObject.InviteCreateBtnListener {

    public final int FRIENDS_ADAPTER_IDX = 0;
    public final int INVITES_ADAPTER_IDX = 1;
    public final int STRANGERS_ADAPTER_IDX = 2;

    private ShutterApiInterface shutterApiInterface; // TODO on null object
    private FriendRemoveRequester friendRemoveRequester;
    private InviteAcceptRequester inviteAcceptRequest;
    private InviteCreateRequester inviteCreateRequester;

    private ManageFriendsView friendsView;

    private int currentTabIdx;
    private String currentKeyword;

    public ManageFriendsPresenter(String apiKey) {
        this.inviteCreateRequester = new InviteCreateRequester(apiKey);
        this.inviteCreateRequester.setListener(this);
        this.inviteAcceptRequest = new InviteAcceptRequester(apiKey);
        this.inviteAcceptRequest.setListener(this);
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

    // UI callbacks

    @Override
    public void onPageShow() {
        this.friendsView.searchClearKeyword();
        this.friendsView.tabForceSelect(0);
        this.setupFriendsList();
    }

    @Override
    public void onTabSelected(int index) {
        this.currentTabIdx = index;
        this.friendsView.hideBar();
        switch (index) {
            case FRIENDS_ADAPTER_IDX: this.setupFriendsList(); break;
            case INVITES_ADAPTER_IDX: this.setupInvitesList(); break;
            case STRANGERS_ADAPTER_IDX: this.setupStrangersList(); break;
        }
    }

    @Override
    public void onKeywordChange(String keyword) {
        this.currentKeyword = keyword;
        this.friendsView.friendsSetKeywordFilter(keyword);
        this.friendsView.invitesSetKeywordFilter(keyword);
        if (keyword.isEmpty()) {
            this.friendsView.strangersListClear();
            if (this.currentTabIdx == this.STRANGERS_ADAPTER_IDX)
                this.showStrangersBarNoKeywordMessage();
        } else { this.shutterApiInterface.searchUsers(keyword); }
    }

    @Override
    public void onRefreshEvent() {
        switch (this.currentTabIdx) {
            case FRIENDS_ADAPTER_IDX:  this.shutterApiInterface.downloadFriends(); break;
            case INVITES_ADAPTER_IDX: this.shutterApiInterface.downloadInvites(); break;
        }
    }

    @Override
    public void onFriendRemoveEvent(int userId) {
        this.friendRemoveRequester.deleteFriend(userId);
    }

    @Override
    public void onInviteAcceptEvent(int userId) {
        this.inviteAcceptRequest.acceptInvite(userId);
    }

    @Override
    public void onInviteRejectEvent(int userId) {
        this.inviteAcceptRequest.rejectInvite(userId);
    }

    @Override
    public void onInviteCreateEvent(int userId) {
        this.inviteCreateRequester.sendInvitation(userId);
    }

    @Override
    public void onInviteRemoveEvent(int userId) {
        this.inviteCreateRequester.removeInvitation(userId);
    }

    private void setupFriendsList() {
        this.friendsView.refreshSetRefreshing(true);
        this.friendsView.searchClearKeyword();
        this.friendsView.searchSetIcon(this.FRIENDS_ADAPTER_IDX);
        this.friendsView.listSetAdapter(this.FRIENDS_ADAPTER_IDX);
        this.shutterApiInterface.downloadFriends();
    }

    private void setupInvitesList() {
        this.friendsView.refreshSetRefreshing(true);
        this.friendsView.searchSetIcon(this.INVITES_ADAPTER_IDX); // TODO just icon
        this.friendsView.listSetAdapter(this.INVITES_ADAPTER_IDX);
        this.shutterApiInterface.downloadInvites();
    }

    private void setupStrangersList() {
        this.friendsView.refreshSetRefreshing(false);
        this.friendsView.searchSetIcon(this.STRANGERS_ADAPTER_IDX);
        this.friendsView.listSetAdapter(this.STRANGERS_ADAPTER_IDX);
        if (this.currentKeyword.isEmpty())
            this.showStrangersBarNoKeywordMessage();
    }

    // Api Requests callbacks

    @Override
    public void onFriendsListDownloaded(int changesCount) {
        this.friendsView.refreshSetRefreshing(false);
        this.friendsView.friendsListUpdate(this.shutterApiInterface.getFriends());
        if (this.currentTabIdx == this.FRIENDS_ADAPTER_IDX) {
            this.showFriendsUpdateMessage(changesCount);
            this.showFriendsBarMessage(this.shutterApiInterface.getFriends().size());
        }
    }

    @Override
    public void onInvitesListDownloaded(int changesCount) {
        this.friendsView.refreshSetRefreshing(false);
        this.friendsView.invitesListUpdate(this.shutterApiInterface.getInvites());
        if (this.currentTabIdx == this.INVITES_ADAPTER_IDX) {
            this.showInvitesUpdateMessage(changesCount);
            this.showInvitesBarMessage(this.shutterApiInterface.getInvites().size());
        }
    }

    @Override
    public void onSearchListDownloaded(ArrayList<StrangerData> strangersList) {
        this.friendsView.strangersListUpdate(strangersList);
        if (this.currentTabIdx == this.STRANGERS_ADAPTER_IDX) {
            this.showStrangersBarMessage(strangersList.size());
        }
    }

    private void showFriendsUpdateMessage(int changesCount) {
        if (changesCount > 0) { this.friendsView.showInfoMessage(changesCount + " friends added"); }
        else if (changesCount < 0) { this.friendsView.showInfoMessage(Math.abs(changesCount) + " friends removed"); }
    }

    private void showFriendsBarMessage(int count) {
        if (count > 0) { this.friendsView.hideBar(); } 
        else { this.friendsView.showBarMessage("unfortunately u re alone by now \n PRO TIP: send some invites ;)"); }
    }

    private void showInvitesUpdateMessage(int changesCount) {
        if (changesCount > 0) { this.friendsView.showInfoMessage(changesCount + " invites added"); }
        else if (changesCount < 0) { this.friendsView.showInfoMessage(Math.abs(changesCount) + " invites removed"); }
    }

    private void showInvitesBarMessage(int count) {
        if (count > 0) { this.friendsView.hideBar(); }
        else { this.friendsView.showBarMessage("no invites yet \n try to search for friends"); }
    }

    private void showStrangersBarMessage(int count) {
        if (count > 0) { this.friendsView.hideBar(); }
        else { this.friendsView.showBarMessage("no users found! \n try to change keyword"); }
    }

    private void showStrangersBarNoKeywordMessage() {
        this.friendsView.showBarMessage("please try to type any keyword \n to find your own friends..");
    }

    @Override
    public void onFriendRemove(int friendId) {
        this.shutterApiInterface.downloadFriends();
    }

    @Override
    public void onInviteRequestAccepted(int userId) {
        this.shutterApiInterface.downloadInvites();
    }

    @Override
    public void onInviteRequestRemoved(int userId) {
        this.shutterApiInterface.downloadInvites();
    }

    @Override
    public void onInviteRequestCreate(int userId) {
        this.shutterApiInterface.reloadSearchResults();
    }

    @Override
    public void onInviteRequestRemove(int userId) {
        this.shutterApiInterface.reloadSearchResults();
    }
}
