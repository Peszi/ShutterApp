package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsDataInterface;
import com.pheasant.shutterapp.shutter.api.interfaces.FriendsListListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class ShutterDataController implements FriendsDataInterface {

//    private ShutterRequestManager shutterRequestManager;

    private FriendsContainer friendsContainer;

    public ShutterDataController(String apiKey) {
//        this.shutterRequestManager = new ShutterRequestManager(apiKey);
        this.friendsContainer = new FriendsContainer(apiKey);
//        this.shutterRequestManager.setFriendsListener(this.friendsContainer);
    }

    // TODO register status listeners

    public void registerFriendsListener(FriendsListListener friendsListener) {
        this.friendsContainer.registerFriendsListener(friendsListener);
    }

    @Override
    public void updateFriends() {
        this.friendsContainer.updateFriendsList();
    }

    @Override
    public ArrayList<FriendData> getFriends() {
        return this.friendsContainer.getFriendsList();
    }

    @Override
    public void updateImages() {

    }
}
