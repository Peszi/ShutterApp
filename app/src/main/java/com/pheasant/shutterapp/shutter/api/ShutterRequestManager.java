package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsRequestListener;
import com.pheasant.shutterapp.shutter.api.util.InternetUtility;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterRequestManager implements RequestResultListener {

    private String apiKey;
    private StatusProvider statusProvider;

    private FriendsListRequest friendsRequest;

    public ShutterRequestManager(String apiKey) {
        this.apiKey = apiKey;
        this.statusProvider = new StatusProvider();

        this.friendsRequest = new FriendsListRequest(this.apiKey);
        this.friendsRequest.setOnRequestResultListener(this);
    }

    public void setFriendsListener(FriendsRequestListener friendsListener) {
        this.friendsRequest.setFriendsRequestListener(friendsListener);
    }

    public void requestFriendsList() {
        if (InternetUtility.isInternetConnection(this.statusProvider)) {
            this.friendsRequest.execute();
        }
    }

    @Override
    public void onResult(int resultCode) {
        // TODO handling errors
    }

    protected StatusProvider getStatusProvider() {
        return this.statusProvider;
    }

}
