package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.friends.FriendsListRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterApi implements RequestResultListener {

    private String apiKey;
    private StatusProvider statusProvider;

    public ShutterApi(String apiKey) {
        this.apiKey = apiKey;
        this.statusProvider = new StatusProvider();
    }

    public void requestFriendsList(FriendsListRequest.FriendsListListener friendsListListener) {
        if (InternetUtility.isInternetConnection(this.statusProvider)) {
            FriendsListRequest friendsRequest = new FriendsListRequest(this.apiKey);
            friendsRequest.setBaseResultListener(friendsListListener);
            friendsRequest.setOnRequestResultListener(this);
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
