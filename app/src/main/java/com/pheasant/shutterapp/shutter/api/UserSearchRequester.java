package com.pheasant.shutterapp.shutter.api;

import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.friends.UserSearchRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;
import com.pheasant.shutterapp.shutter.api.util.UserSearchListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public class UserSearchRequester implements RequestResultListener {

    private UserSearchRequest userSearchRequest;
    private SearchListListener searchListener;

    public UserSearchRequester(String apiKey) {
        this.userSearchRequest = new UserSearchRequest(apiKey);
        this.userSearchRequest.setOnRequestResultListener(this);
    }

    public void setSearchListener(SearchListListener searchListener) {
        this.searchListener = searchListener;
    }

    public void searchByKeyword(String keyword) {
        this.userSearchRequest.cancel();
        this.userSearchRequest.setKeyword(keyword);
        this.userSearchRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK)
            this.notifyListeners(this.userSearchRequest.getStrangersList());
    }

    private void notifyListeners(ArrayList<StrangerData> usersList) {
        if (this.searchListener != null)
            this.searchListener.onSearchListDownloaded(usersList);
    }
}
