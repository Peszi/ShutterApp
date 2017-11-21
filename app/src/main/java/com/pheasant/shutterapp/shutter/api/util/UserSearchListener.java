package com.pheasant.shutterapp.shutter.api.util;

import com.pheasant.shutterapp.network.request.data.StrangerData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public interface UserSearchListener {
    void onUserSearchResult(ArrayList<StrangerData> usersList);
}
