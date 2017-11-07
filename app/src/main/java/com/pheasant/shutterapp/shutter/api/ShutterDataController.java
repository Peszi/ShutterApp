package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.shutter.api.data.FriendsContainer;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsDataInterface;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataController implements FriendsDataInterface {

    private ShutterApi shutterApi;

    private FriendsContainer friendsContainer;

    public ShutterDataController(String apiKey) {
        this.shutterApi = new ShutterApi(apiKey);
        this.friendsContainer = new FriendsContainer(this.shutterApi.getStatusProvider());
    }

    // TODO register status listeners

    @Override
    public void updateFriends() {
        this.friendsContainer.updateFriendsList(this.shutterApi);
    }

    @Override
    public void updateImages() {

    }
}
