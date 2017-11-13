package com.pheasant.shutterapp.shutter.api.interfaces;

import com.pheasant.shutterapp.network.request.data.FriendData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-09.
 */

public interface FriendsListListener {
    void onFriendsUpdate(ArrayList<FriendData> friendsList);
}
