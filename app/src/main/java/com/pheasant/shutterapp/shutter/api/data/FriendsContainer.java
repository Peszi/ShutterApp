package com.pheasant.shutterapp.shutter.api.data;

import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.pheasant.shutterapp.network.download.UserDataDownloader;
import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.ShutterApi;
import com.pheasant.shutterapp.shutter.api.util.ServerMessage;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-11-06.
 */

public class FriendsContainer implements FriendsListRequest.FriendsListListener {

    private ArrayList<FriendData> friendsList;

    private StatusProvider statusProvider;

    public FriendsContainer(StatusProvider statusProvider) {
        this.friendsList = new ArrayList<>();
        this.statusProvider = statusProvider;
    }

    public void updateFriendsList(ShutterApi shutterApi) {
        Log.d("RESPONSE", "downloading friends list!");
        shutterApi.requestFriendsList(this);
    }

    @Override
    public void onListUpdated(List<FriendData> friendsList) {
//        for (UserData newUserData : this.friendsListRequest.getFriendsList()) {
//            UserData userData = this.getFriendData(newUserData);
//            if (userData != null) {
//                userData.setName(newUserData.getName());
//                userData.setAvatar(newUserData.getAvatar());
//            } else {
//                this.userDataList.add(newUserData);
//            }
//        }
//        if (this.onFriendsListListener != null)
//            this.onFriendsListListener.onFriendsDownloaded(this.userDataList);
    }
}
