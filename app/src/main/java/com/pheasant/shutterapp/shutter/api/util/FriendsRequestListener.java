package com.pheasant.shutterapp.shutter.api.util;

import com.pheasant.shutterapp.network.request.data.FriendData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-07.
 */

public interface FriendsRequestListener {
    void onFriendsListDownloaded(ArrayList<FriendData> friendsList);
}
