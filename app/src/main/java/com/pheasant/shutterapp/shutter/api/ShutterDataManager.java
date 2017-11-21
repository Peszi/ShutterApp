package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.shutter.api.container.FriendsListContainer;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.api.requester.InvitesListRequester;
import com.pheasant.shutterapp.shutter.api.requester.SearchListRequester;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataManager implements ShutterApiInterface {

    private SearchListRequester searchListRequester;
    private InvitesListRequester invitesListRequester;

    private FriendsListContainer friendsListContainer;

    public ShutterDataManager(String apiKey) {
        this.searchListRequester = new SearchListRequester(apiKey);
        this.invitesListRequester = new InvitesListRequester(apiKey);
        this.friendsListContainer = new FriendsListContainer(apiKey);
    }

    @Override
    public void registerFriendsListListener(FriendsListListener listListener) {
        this.friendsListContainer.registerFriendsListener(listListener);
    }

    @Override
    public void setSearchListListener(SearchListListener listListener) {
        this.searchListRequester.setListListener(listListener);
    }

    @Override
    public void setInvitesListListener(InvitesListListener listListener) {
        this.invitesListRequester.setListListener(listListener);
    }

    @Override
    public void downloadFriends() {
        this.friendsListContainer.downloadFriendsList();
    }

    @Override
    public void downloadInvites() {
        this.invitesListRequester.downloadList();
    }

    @Override
    public void searchUsers(String keyword) {
        this.searchListRequester.searchByKeyword(keyword);
    }

    @Override
    public ArrayList<FriendData> getFriends() {
        return this.friendsListContainer.getFriendsList();
    }

    @Override
    public ArrayList<UserData> getInvites() {
        return this.invitesListRequester.getInvitesList();
    }

}
