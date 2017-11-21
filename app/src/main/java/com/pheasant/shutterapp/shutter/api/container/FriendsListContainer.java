package com.pheasant.shutterapp.shutter.api.container;

import android.util.Log;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.util.FriendsRequestListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class FriendsListContainer implements FriendsRequestListener {

    private ArrayList<FriendData> friendsList;

    private FriendsListRequest friendsRequest;
    private ArrayList<FriendsListListener> friendsListeners;

    public FriendsListContainer(String apiKey) {
        this.friendsList = new ArrayList<>();
        this.friendsListeners = new ArrayList<>();
        this.friendsRequest = new FriendsListRequest(apiKey);
        this.friendsRequest.setFriendsRequestListener(this);
//        this.friendsRequest.setOnRequestResultListener(this);
    }

    public void registerFriendsListener(FriendsListListener friendsListener) {
        this.friendsListeners.add(friendsListener);
    }

    public void downloadFriendsList() {
        this.friendsRequest.cancel();
        this.friendsRequest.execute();
    }

    @Override
    public void onFriendsListDownloaded(ArrayList<FriendData> newFriendsList) {
        ArrayList<FriendData> addedFriendsList = new ArrayList<>();
        for (FriendData newFriend : newFriendsList) {
            if (!this.updateFriend(newFriend)) {// if users is NOT in our list
                this.friendsList.add(newFriend);
                addedFriendsList.add(newFriend);
            }
        }
        this.notifyListeners(addedFriendsList);
    }

    // Updating friend data if user already exist
    private boolean updateFriend(FriendData updatedFriend) {
        for (FriendData friend : this.friendsList)
            if (friend.getId() == updatedFriend.getId()) {
                friend.update(updatedFriend);
                return true;
            }
        return false;
    }

    private void notifyListeners(ArrayList<FriendData> newFriendsList) {
        for (FriendsListListener friendsListener : this.friendsListeners)
            friendsListener.onFriendsListDownloaded(newFriendsList);
    }

    public ArrayList<FriendData> getFriendsList() {
        return this.friendsList;
    }
}
