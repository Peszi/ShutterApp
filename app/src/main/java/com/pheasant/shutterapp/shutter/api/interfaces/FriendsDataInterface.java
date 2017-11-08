package com.pheasant.shutterapp.shutter.api.interfaces;

import com.pheasant.shutterapp.network.request.data.FriendData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-06.
 */

public interface FriendsDataInterface {
    void updateFriends();
    ArrayList<FriendData> getFriends();
    void updateImages();
}
