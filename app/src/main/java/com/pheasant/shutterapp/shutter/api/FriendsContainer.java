package com.pheasant.shutterapp.shutter.api;

import android.util.Log;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsListListener;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsRequestListener;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class FriendsContainer implements FriendsRequestListener {

    private ArrayList<FriendData> friendsList;
    private ArrayList<FriendsListListener> friendsListeners;

    private FriendsListRequest friendsRequest;

    public FriendsContainer(String apiKey) {
        this.friendsList = new ArrayList<>();
        this.friendsListeners = new ArrayList<>();
        this.friendsRequest = new FriendsListRequest(apiKey);
        this.friendsRequest.setFriendsRequestListener(this);
//        this.friendsRequest.setOnRequestResultListener(this);
    }

    public void registerFriendsListener(FriendsListListener friendsListener) {
        this.friendsListeners.add(friendsListener);
    }

    protected void updateFriendsList() {
        Log.d("RESPONSE", "[friends list downloading...]");
        this.friendsRequest.execute();
    }

    @Override
    public void onListUpdated(ArrayList<FriendData> newFriendsList) {
        int newFriends = this.friendsList.size();
        for (FriendData newFriend : newFriendsList)
            if (!this.updateFriend(newFriend)) // if users is NOT in our list
                this.friendsList.add(newFriend); // TODO notify friends list listeners
        newFriends = this.friendsList.size() - newFriends;
        Log.d("RESPONSE", "[friends list updated with " + newFriends + " new users]");
        this.notifyListeners();
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

    private void notifyListeners() {
        for (FriendsListListener friendsListener : this.friendsListeners)
            friendsListener.onFriendsUpdate(this.friendsList);
    }

    protected ArrayList<FriendData> getFriendsList() {
        return this.friendsList;
    }
}
