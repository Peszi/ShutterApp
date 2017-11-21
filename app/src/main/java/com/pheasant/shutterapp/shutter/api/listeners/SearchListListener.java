package com.pheasant.shutterapp.shutter.api.listeners;

import com.pheasant.shutterapp.network.request.data.StrangerData;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public interface SearchListListener {
    void onSearchListDownloaded(ArrayList<StrangerData> friendsList);
}
