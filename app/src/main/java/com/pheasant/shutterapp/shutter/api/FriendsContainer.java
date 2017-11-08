package com.pheasant.shutterapp.shutter.api;

import android.util.Log;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsResultListener;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class FriendsContainer implements FriendsResultListener {

    private ArrayList<FriendData> friendsList;

    private StatusProvider statusProvider;

    public FriendsContainer(StatusProvider statusProvider) {
        this.friendsList = new ArrayList<>();
        this.statusProvider = statusProvider;
    }

    protected void updateFriendsList(ShutterRequestManager shutterRequestManager) {
        Log.d("RESPONSE", "[friends list downloading...]");
        shutterRequestManager.requestFriendsList(this);
    }

    @Override
    public void onListUpdated(ArrayList<FriendData> newFriendsList) {
        int newFriends = this.friendsList.size();
        for (FriendData newFriend : newFriendsList)
            if (!this.updateFriend(newFriend)) // if users is NOT in our list
                this.friendsList.add(newFriend); // TODO notify friends list listeners
        newFriends = this.friendsList.size() - newFriends;
        Log.d("RESPONSE", "[friends list updated with " + newFriends + " new users]");
//        if (this.onFriendsListListener != null)
//            this.onFriendsListListener.onFriendsDownloaded(this.userDataList);
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

    protected ArrayList<FriendData> getFriendsList() {
        return this.friendsList;
    }
}
