package com.pheasant.shutterapp.shutter.api.requester;

import com.pheasant.shutterapp.network.request.data.StrangerData;
import com.pheasant.shutterapp.network.request.friends.UserSearchRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.shutter.api.listeners.SearchListListener;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public class SearchListRequester implements RequestResultListener {

    private UserSearchRequest listRequest;
    private SearchListListener listListener;

    public SearchListRequester(String apiKey) {
        this.listRequest = new UserSearchRequest(apiKey);
        this.listRequest.setOnRequestResultListener(this);
    }

    public void setListListener(SearchListListener listListener) {
        this.listListener = listListener;
    }

    public void searchByKeyword(String keyword) {
        this.listRequest.cancel();
        this.listRequest.setKeyword(keyword);
        this.listRequest.execute();
    }

    public void reloadList() {
        this.listRequest.cancel();
        this.listRequest.execute();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK)
            this.notifyListeners(this.listRequest.getStrangersList());
    }

    private void notifyListeners(ArrayList<StrangerData> usersList) {
        if (this.listListener != null)
            this.listListener.onSearchListDownloaded(usersList);
    }
}
