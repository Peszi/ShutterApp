package com.pheasant.shutterapp.features.shutter;

import com.pheasant.shutterapp.network.request.data.UserData;

import java.util.List;

/**
 * Created by Peszi on 2017-05-20.
 */

public interface DataListener {
    com.pheasant.shutterapp.network.download.user.UserData getUserData();
    List<UserData> getFriendsData();
}
