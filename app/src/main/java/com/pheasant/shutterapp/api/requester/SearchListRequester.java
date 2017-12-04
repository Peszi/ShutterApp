package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.data.StrangerData;
import com.pheasant.shutterapp.api.request.UserSearchRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.SearchListListener;
import com.pheasant.shutterapp.api.util.Request;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-20.
 */

public class SearchListRequester implements RequestResultListener {

    private UserSearchRequest listRequest;
    private SearchListListener listListener;

    public SearchListRequester(String apiKey) {
        this.listRequest = new UserSearchRequest(apiKey);
        this.listRequest.setRequestListener(this);
    }

    public void setListListener(SearchListListener listListener) {
        this.listListener = listListener;
    }

    public void searchByKeyword(String keyword) {
        this.listRequest.setKeyword(keyword);
    }

    public void reloadList() {
        this.listRequest.sendRequest();
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
