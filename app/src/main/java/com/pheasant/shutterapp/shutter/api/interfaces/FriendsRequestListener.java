package com.pheasant.shutterapp.shutter.api.interfaces;

import com.pheasant.shutterapp.network.request.data.FriendData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-07.
 */

public interface FriendsRequestListener {
    void onListUpdated(ArrayList<FriendData> friendsList);
}
