package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shutter.api.interfaces.ShutterApiInterface;
import com.pheasant.shutterapp.shutter.api.interfaces.UserSearchInterface;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataManager implements ShutterApiInterface {

    private UserSearchRequester userSearchRequester;

    private FriendsListContainer friendsListContainer;

    public ShutterDataManager(String apiKey) {
        this.userSearchRequester = new UserSearchRequester(apiKey);
        this.friendsListContainer = new FriendsListContainer(apiKey);
    }

    @Override
    public void registerFriendsListListener(FriendsListListener listListener) {
        this.friendsListContainer.registerFriendsListener(listListener);
    }

    @Override
    public void setSearchListListener(SearchListListener listListener) {
        this.userSearchRequester.setSearchListener(listListener);
    }

    @Override
    public void registerInvitesListListener(FriendsListListener listListener) {

    }

    @Override
    public void downloadFriends() {
        this.friendsListContainer.updateFriendsList();
    }

    @Override
    public void searchUsers(String keyword) {
        this.userSearchRequester.searchByKeyword(keyword);
    }

    @Override
    public ArrayList<FriendData> getFriends() {
        return this.friendsListContainer.getFriendsList();
    }

}
