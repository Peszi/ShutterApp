package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.shutter.api.data.FriendsContainer;
import com.pheasant.shutterapp.shutter.api.interfaces.ApiRequestInterface;
import com.pheasant.shutterapp.shutter.api.util.ServerMessage;
import com.pheasant.shutterapp.shutter.api.util.StatusProvider;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterData implements ApiRequestInterface {

    private ShutterApi shutterApi;
    private StatusProvider statusProvider;

    private FriendsContainer friendsContainer;

    public ShutterData(String apiKey) {
        this.shutterApi = new ShutterApi(apiKey);
        this.statusProvider = new StatusProvider();
        this.friendsContainer = new FriendsContainer();
    }

    // TODO register status listeners

    @Override
    public void updateFriends() {
        if (InternetUtility.isInternetConnection(this.statusProvider)) {
            this.friendsContainer.updateFriendsList(this.shutterApi);
        }
    }

    @Override
    public void updateImages() {

    }
}
