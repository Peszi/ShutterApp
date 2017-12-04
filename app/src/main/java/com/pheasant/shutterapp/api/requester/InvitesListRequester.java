package com.pheasant.shutterapp.api.requester;

import com.pheasant.shutterapp.api.data.UserData;
import com.pheasant.shutterapp.api.request.InvitesListRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.listeners.InvitesListListener;
import com.pheasant.shutterapp.api.util.Request;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-21.
 */

public class InvitesListRequester implements RequestResultListener {

    private ArrayList<UserData> invitesList;

    private InvitesListRequest listRequest;
    private InvitesListListener listListener;

    public InvitesListRequester(String apiKey) {
        this.invitesList = new ArrayList<>();
        this.listRequest = new InvitesListRequest(apiKey);
        this.listRequest.setRequestListener(this);
    }

    public void setListListener(InvitesListListener listListener) {
        this.listListener = listListener;
    }

    public void downloadList() {
        this.listRequest.sendRequest();
    }

    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            final int changesCount = this.listRequest.getInvitesList().size() - this.invitesList.size();
            this.invitesList.clear();
            this.invitesList.addAll(this.listRequest.getInvitesList());
            this.notifyListeners(changesCount);
        }
    }

    private void notifyListeners(int changesCount) {
        if (this.listListener != null)
            this.listListener.onInvitesListDownloaded(changesCount);
    }

    public ArrayList<UserData> getInvitesList() {
        return this.invitesList;
    }
}
