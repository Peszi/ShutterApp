package com.pheasant.shutterapp.features.shutter.manage.friends;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-06-06.
 */

public class FriendsListDownloader implements RequestResultListener {

    private List<FriendData> userDataList;
    private FriendsListRequest friendsListRequest;
    private FriendsListener onFriendsListListener;

    public FriendsListDownloader(String apiKey) {
        this.userDataList = new ArrayList<>();
        this.friendsListRequest = new FriendsListRequest(apiKey);
        this.friendsListRequest.setOnRequestResultListener(this);
    }

    public void setOnFriendsListListener(FriendsListener onFriendsListListener) {
        this.onFriendsListListener = onFriendsListListener;
    }

    public void download() {
        this.friendsListRequest.execute();
    }

    //TODO no internet connection message

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            for (FriendData newUserData : this.friendsListRequest.getFriendsList()) {
                FriendData userData = this.getFriendData(newUserData);
                if (userData != null) {
                    userData.setName(newUserData.getName());
                    userData.setAvatar(newUserData.getAvatar());
                } else {
                    this.userDataList.add(newUserData);
                }
            }
            if (this.onFriendsListListener != null) {
                this.onFriendsListListener.onSuccess(this.friendsListRequest.getFriendsList());
                //this.onFriendsListListener.onError("successfully reloaded!");
            }
        } else {
            if (this.onFriendsListListener != null)
                this.onFriendsListListener.onError("error occurred, try again");
        }
    }

    private FriendData getFriendData(FriendData newUserData) {
        for (FriendData userData : this.userDataList)
            if (userData.getId() == newUserData.getId())
                return userData;
        return null;
    }
}
