package com.pheasant.shutterapp.shutter.api.data;

import com.pheasant.shutterapp.network.request.data.FriendData;
import com.pheasant.shutterapp.shutter.api.ShutterApi;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public class FriendsContainer {

    private ArrayList<FriendData> friendsList;

    public FriendsContainer() {
        this.friendsList = new ArrayList<>();
    }

    public void updateFriendsList(ShutterApi shutterApi) {

    }
}
