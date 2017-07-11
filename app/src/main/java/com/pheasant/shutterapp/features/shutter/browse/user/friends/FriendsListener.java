package com.pheasant.shutterapp.features.shutter.browse.user.friends;

import com.pheasant.shutterapp.network.request.data.FriendData;

import java.util.List;

/**
 * Created by Peszi on 2017-06-06.
 */

public interface FriendsListener {
    void onSuccess(List<FriendData> friendsList);
    void onError(String message);
}
